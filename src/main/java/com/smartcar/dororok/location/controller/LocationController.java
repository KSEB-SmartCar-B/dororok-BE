package com.smartcar.dororok.location.controller;

import com.smartcar.dororok.location.dto.LocationDto;
import com.smartcar.dororok.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/info")
    public LocationDto locationInfo(@RequestParam String lat, @RequestParam String lng) {
        return locationService.getAddressFromCoordinates(lat, lng);
    }
}
