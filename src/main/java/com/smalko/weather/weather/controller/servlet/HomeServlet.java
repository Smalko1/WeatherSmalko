package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.location.json.SearchWeatherForCoordinates;
import com.smalko.weather.weather.location.result.SearchWeatherResult;
import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", value = UrlPath.HOME)
public class HomeServlet extends BaseServlet {
    private List<SearchWeatherForCoordinates> weatherForCity;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        putAttributeInModel("home", true);

        var searchWeatherResult = (SearchWeatherResult) request.getSession().getAttribute("SearchWeatherResult");
        if (searchWeatherResult != null) {
            if (searchWeatherResult.isSuccessful()) {
                weatherForCity = searchWeatherResult.getWeather();
                putAttributeInModel("searchWeatherResult", weatherForCity);
            } else
                putAttributeInModel("searchWeatherException", searchWeatherResult.getException());
            request.getSession().removeAttribute("SearchWeatherResult");
        }


        super.processTemplate("home", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        var selectedCityId = request.getParameter("selectedCityId");
        if (selectedCityId != null){
            var searchWeatherForCoordinates = weatherForCity.get(Integer.parseInt(selectedCityId));
        }

    }
}

/*

<div th:if="${model.weather != null}" class="d-flex flex-row mt-4 overflow-auto mt-4">
        <div class="card mx-2" style="width: 18rem;">
            <div class="card-body">
                <h5 class="card-title" th:text="${model.city.name}"></h5>
                <p class="card-text" th:text="|temp ${model.weather.current.temp}|"></p>
                <p class="card-text" th:text="|feels like ${model.weather.current.feelsLike}|"></p>
                <button type="submit" class="btn btn-primary" th:method="doPost">See more</button>
            </div>
        </div>
    </div>


    var selectedCityId = request.getParameter("selectedCityId");
        if (selectedCityId != null){
            var searchCity = searchCities.get(Integer.parseInt(selectedCityId));
            var searchWeatherResult = OpenWeatherAPI.requestWeatherByCoordinate(searchCity.getLat(), searchCity.getLon());
            if (searchWeatherResult.getStatus().isOK()){
                putAttributeInModel("city", searchCity.getName());
                putAttributeInModel("weather", searchWeatherResult.getWeather());
                super.processTemplate("home", request, response);
            }else {
                putAttributeInModel("UnsuccessfulSearch", "Unsuccessful search");
            }
        }
 */
