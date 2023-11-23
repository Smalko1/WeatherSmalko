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
// TODO: 23.11.2023 Придумать название метода
        extracted(request);

        var userId = (Integer) request.getSession().getAttribute("userId");
        if (userId != null){
            // TODO: 23.11.2023 написать код который будет брать с БД человека все его локации и показовать везде там погоду
        }
        super.processTemplate("home", request, response);
    }

    private void extracted(HttpServletRequest request) {
        var cityName = (String) request.getSession().getAttribute("cityName");
        var lat = (String) request.getSession().getAttribute("lat");
        var lon = (String) request.getSession().getAttribute("lon");
        var seeMore = (String) request.getSession().getAttribute("seeMore");
        var favorites = (String) request.getSession().getAttribute("favorites");
        if (cityName != null && lat != null && lon != null) {
            if (seeMore != null){
                log.info("User wants to see the weather of a location {}", cityName);
                var searchWeather = OpenWeatherAPI.requestWeather(Double.valueOf(lat), Double.valueOf(lon), cityName);
                putAttributeInModel("seeMore", Boolean.parseBoolean(seeMore));
                putAttributeInModel("weather", searchWeather);
                request.getSession().removeAttribute("seeMore");
            }else if (favorites != null) {
                log.info("User wants to add location in favorites list");
                // TODO: 23.11.2023 Поздать логику сохранения локациии в БД
                var createLocationDto = CreateLocationDto.builder()
                        .name(cityName)
                        .latitude(Double.valueOf(lat))
                        .longitude(Double.valueOf(lon))
                        .build();
                LocationService.getInstance().saveLocation(createLocationDto);

                putAttributeInModel("seeMore", Boolean.parseBoolean(favorites));
            //Location.save()
                request.getSession().removeAttribute("favorites");
            }
            request.getSession().removeAttribute("cityName");
            request.getSession().removeAttribute("lat");
            request.getSession().removeAttribute("lon");
        }
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

        //Написать обработку кнопки добавить в избраное, выводить лист своих избранных
        //Розобраться с проблемой ввода названия городв в которых есть 2 и больше слов которые рразделены -
    }
}