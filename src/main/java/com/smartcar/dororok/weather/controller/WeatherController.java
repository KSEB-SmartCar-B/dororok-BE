package com.smartcar.dororok.weather.controller;

import com.smartcar.dororok.weather.domain.GetWeatherDto;
import com.smartcar.dororok.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;


    @GetMapping("/currnet-weather")
    public GetWeatherDto getWeather(@RequestParam String lat, @RequestParam String lng) {
        return weatherService.getCurrentWeather(lat,lng);
    }
}
