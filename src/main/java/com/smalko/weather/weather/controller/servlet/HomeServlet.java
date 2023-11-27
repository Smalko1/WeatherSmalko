package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.location.dto.CreateLocationDto;
import com.smalko.weather.weather.location.result.SearchCity;
import com.smalko.weather.weather.location.service.LocationService;
import com.smalko.weather.weather.location.service.OpenWeatherAPI;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.smalko.weather.weather.util.UrlPath.HOME;

@WebServlet(name = "HomeServlet", value = HOME)
public class HomeServlet extends BaseServlet {
    private static final Logger log = LoggerFactory.getLogger(HomeServlet.class);
    public static final String ATTRIBUTE_SEARCH_CITY = "searchCity";
    public static final String ATTRIBUTE_CITY_NAME = "cityName";
    public static final String ATTRIBUTE_LAT = "lat";
    public static final String ATTRIBUTE_LON = "lon";
    public static final String ATTRIBUTE_SEE_MORE = "seeMore";
    public static final String ATTRIBUTE_FAVORITE = "favorite";
    public static final String ATTRIBUTE_REMOVE_LOCATION_SUCCESSFUL = "removeLocationSuccessful";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        putAttributeInModel("home", true);

        var searchCity = (SearchCity) request.getSession().getAttribute(ATTRIBUTE_SEARCH_CITY);
        if (searchCity != null) {
            getSearchCity(searchCity);
        }

        var cityName = (String) request.getSession().getAttribute(ATTRIBUTE_CITY_NAME);
        var lat = (String) request.getSession().getAttribute(ATTRIBUTE_LAT);
        var lon = (String) request.getSession().getAttribute(ATTRIBUTE_LON);
        var seeMore = (String) request.getSession().getAttribute(ATTRIBUTE_SEE_MORE);
        if (cityName != null && lat != null && lon != null && seeMore != null) {
            log("Search location weather");
            handleSeeMoreRequest(cityName, lat, lon, seeMore);

        }

        var userId = getUserId(request);
        if (userId != null && seeMore == null && searchCity == null) {
            var favoritesLocations = LocationService.getInstance().findAllFavoriteUserLocations(userId);

            if (favoritesLocations.isSuccessful()) {
                putAttributeInModel("favoritesLocations", favoritesLocations);
            } else
                putAttributeInModel("favoritesLocations", "Oops, there was an error, we can't show you your favorite locations.");
            // TODO: 25.11.2023 Написать код в home.html который будет отброжать и проверять на наличие FavoritesLocations
        }
        var removeLocationSuccessful = (Boolean) request.getSession().getAttribute(ATTRIBUTE_REMOVE_LOCATION_SUCCESSFUL);
        if (removeLocationSuccessful != null) {
            putAttributeInModel("removeLocationSuccessful", false);
            // TODO: 26.11.2023 Если удаление fulls то нужно показать что не удалось удалить сущность

        }
        removeSessionAttribute(request, ATTRIBUTE_CITY_NAME, ATTRIBUTE_LON, ATTRIBUTE_LAT, ATTRIBUTE_FAVORITE, ATTRIBUTE_SEE_MORE, ATTRIBUTE_SEARCH_CITY, ATTRIBUTE_REMOVE_LOCATION_SUCCESSFUL);
        super.processTemplate("home", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var cityName = request.getParameter("name");
        var lat = request.getParameter("lat");
        var lon = request.getParameter("lon");
        var seeMore = request.getParameter("seeMore");
        var favorite = request.getParameter("favorites");
        var removeLocation = request.getParameter("removeLocation");

        if (cityName != null && lat != null && lon != null) {
            if (seeMore != null) {
                request.getSession().setAttribute(ATTRIBUTE_SEE_MORE, seeMore);
                log.info("User searches for this {}", cityName);
                request.getSession().setAttribute(ATTRIBUTE_CITY_NAME, cityName);
                request.getSession().setAttribute(ATTRIBUTE_LAT, lat);
                request.getSession().setAttribute(ATTRIBUTE_LON, lon);
                response.sendRedirect(request.getContextPath() + HOME);
                return;
            }

            if (favorite != null) {
                log("Add location in favorite list");
                addLocationInFavorite(request, cityName, lat, lon);
                response.sendRedirect(request.getContextPath() + HOME);
                return;
            }
        }

        if (removeLocation != null) {
            var userId = getUserId(request);
            var removeLocationSuccessful = LocationService.getInstance().removeLocationInUser(userId, Integer.valueOf(removeLocation));
            if (!removeLocationSuccessful) {
                request.getSession().setAttribute(ATTRIBUTE_REMOVE_LOCATION_SUCCESSFUL, false);
            }
            response.sendRedirect(request.getContextPath() + HOME);
            return;
        }
        super.doPost(request, response);

        // TODO: 23.11.2023 Если пользователь не авторизоаван то когда он нажимает добавить до фаворитов то он должен авторизороваться и перекинуться на страниуу login
    }

    private void handleSeeMoreRequest(String cityName, String lat, String lon, String seeMore) {
        log.info("User wants to see the weather of a location {}", cityName);
        var searchWeather = OpenWeatherAPI.requestWeather(Double.valueOf(lat), Double.valueOf(lon), cityName);
        putAttributeInModel("seeMore", Boolean.parseBoolean(seeMore));
        putAttributeInModel("seeWeather", searchWeather);
    }

    private void addLocationInFavorite(HttpServletRequest request, String cityName, String lat, String lon) {
        log.info("User wants to add location in favorites list");
        var createLocationDto = CreateLocationDto.builder()
                .userId(getUserId(request))
                .name(cityName)
                .latitude(Double.valueOf(lat))
                .longitude(Double.valueOf(lon))
                .build();

        LocationService.getInstance().saveLocationInUser(createLocationDto);
    }

    private void getSearchCity(SearchCity searchCity) {
        log.info("searchCity is not null");
        if (searchCity.isSuccessful()) {
            log.info("searchCity is successful");
            putAttributeInModel("searchCityList", searchCity.getSearchCityLists());
        } else {
            log.error("searchCity is unsuccessful", searchCity.getException());
            putAttributeInModel("searchCityException", searchCity.getException());
        }
    }

    private static Integer getUserId(HttpServletRequest request) {
        var userId = (Integer) request.getSession().getAttribute("userId");
        log.info("user id = {}", userId);
        return userId;
    }

}