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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        putAttributeInModel("home", true);

        var searchCity = (SearchCity) request.getSession().getAttribute("searchCity");
        if (searchCity != null) {
            getSearchCity(request, searchCity);
            request.getSession().removeAttribute("searchCity");
        }

        var cityName = (String) request.getSession().getAttribute("cityName");
        var lat = (String) request.getSession().getAttribute("lat");
        var lon = (String) request.getSession().getAttribute("lon");
        var seeMore = (String) request.getSession().getAttribute("seeMore");
        var favorites = (String) request.getSession().getAttribute("favorites");
        if (cityName != null && lat != null && lon != null) {
            if (seeMore != null) {
                handleSeeMoreRequest(request, cityName, lat, lon, seeMore);
            } else if (favorites != null) {
                processLocationAttributes(request, cityName, lat, lon);
            }
            request.getSession().removeAttribute("cityName");
            request.getSession().removeAttribute("lat");
            request.getSession().removeAttribute("lon");

            var userId = getUserId(request);
            if (userId != null && seeMore == null && searchCity == null) {
                var favoritesLocations = LocationService.getInstance().findAllFavoriteUserLocations(userId);

                if (favoritesLocations.isSuccessful()) {
                    putAttributeInModel("FavoritesLocations", favoritesLocations);
                }
                // TODO: 25.11.2023 Написать код в home.html который будет отброжать и проверять на наличие FavoritesLocations
            }
        }

        super.processTemplate("home", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var name = request.getParameter("name");
        var lat = request.getParameter("lat");
        var lon = request.getParameter("lon");
        var seeMore = request.getParameter("seeMore");
        var favorites = request.getParameter("favorites");

        if (name != null && lat != null && lon != null) {
            if (seeMore != null) {
                request.getSession().setAttribute("seeMore", seeMore);
            }
            if (favorites != null) {
                request.getSession().setAttribute("favorites", favorites);
            }
            log.info("User searches for this {}", name);
            request.getSession().setAttribute("cityName", name);
            request.getSession().setAttribute("lat", lat);
            request.getSession().setAttribute("lon", lon);
        }
        super.doPost(request, response);

        // TODO: 23.11.2023 Если пользователь не авторизоаван то когда он нажимает добавить до фаворитов то он должен авторизороваться и перекинуться на страниуу login
        //Написать обработку кнопки добавить в избраное, выводить лист своих избранных
        //Розобраться с проблемой ввода названия городв в которых есть 2 и больше слов которые рразделены -
    }

    private void handleSeeMoreRequest(HttpServletRequest request, String cityName, String lat, String lon, String seeMore) {
        log.info("User wants to see the weather of a location {}", cityName);
        var searchWeather = OpenWeatherAPI.requestWeather(Double.valueOf(lat), Double.valueOf(lon), cityName);
        putAttributeInModel("seeMore", Boolean.parseBoolean(seeMore));
        putAttributeInModel("weather", searchWeather);
        request.getSession().removeAttribute("seeMore");
    }

    private static void processLocationAttributes(HttpServletRequest request, String cityName, String lat, String lon) {
        log.info("User wants to add location in favorites list");

        var createLocationDto = CreateLocationDto.builder()
                .userId(getUserId(request))
                .name(cityName)
                .latitude(Double.valueOf(lat))
                .longitude(Double.valueOf(lon))
                .build();
        LocationService.getInstance().saveLocationInUser(createLocationDto);
        request.getSession().removeAttribute("favorites");
    }

    private void getSearchCity(HttpServletRequest request, SearchCity searchCity) {
        log.info("searchCity is not null");
        if (searchCity.isSuccessful()) {
            log.info("searchCity is successful");
            putAttributeInModel("searchCityList", searchCity.getSearchCityLists());
        } else {
            log.info("searchCity is  unsuccessful");
            putAttributeInModel("searchCityException", searchCity.getException());
        }
        request.getSession().removeAttribute("searchCity");
    }

    private static Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getSession().getAttribute("userId");
    }


}