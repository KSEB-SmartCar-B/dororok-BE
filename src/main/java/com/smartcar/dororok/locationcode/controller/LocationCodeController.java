package com.smartcar.dororok.locationcode.controller;

import com.smartcar.dororok.locationcode.domain.LocationCode;
import com.smartcar.dororok.locationcode.domain.dto.LocationCodeDto;
import com.smartcar.dororok.locationcode.domain.req.LocationCodeReq;
import com.smartcar.dororok.locationcode.service.LocationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("locationcode")
public class LocationCodeController {

    private final LocationCodeService locationCodeService;

//    @GetMapping
//    public ResponseEntity<String> getLocationCode() {
//        locationCodeService.save();
//        return ResponseEntity.ok("success");
//    }

    @GetMapping
    public ResponseEntity<LocationCodeDto> getLocationCode(@RequestParam String area, @RequestParam String sigungu) {
        return ResponseEntity.ok(locationCodeService.getLocationCode(LocationCodeReq.builder()
                .region1depthName(area)
                .region2depthName(sigungu)
                .build()));
    }
}
