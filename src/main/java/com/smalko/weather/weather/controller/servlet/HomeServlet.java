package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.location.json.SearchCityList;
import com.smalko.weather.weather.location.result.SearchWeatherResult;
import com.smalko.weather.weather.location.result.api.SearchCityResult;
import com.smalko.weather.weather.location.service.OpenWeatherAPI;
import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.smalko.weather.weather.util.UrlPath.HOME;

@WebServlet(name = "HomeServlet", value = UrlPath.HOME)
public class HomeServlet extends BaseServlet {
    private List<SearchCityList> searchCities;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        putAttributeInModel("home", true);

        var searchCityResult = (SearchCityResult) request.getSession().getAttribute("searchCityResult");
        if(searchCityResult != null){
            searchCities = searchCityResult.getSearchCityList();
            putAttributeInModel("searchCityResult", searchCityResult);
        }
        request.getSession().removeAttribute("searchCityResult");

        super.processTemplate("home", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);


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
