package com.smartcar.dororok.destination.controller;

import com.smartcar.dororok.destination.domain.dto.DestinationDto;
import com.smartcar.dororok.destination.service.DestinationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/destination")
@Tag(name = "Destination Controller", description = "목적지 관련 API")
public class DestinationController {

    private final DestinationService destinationService;

    @PostMapping
    @Operation(summary = "목적지를 장소 추천을 위한 DB에 추가", description = "y값 : lat, x값 : lng")
    public ResponseEntity<String> postDestination(@RequestBody DestinationDto dto) {

        destinationService.postDestination(dto.getLat(),dto.getLng());
        return ResponseEntity.ok("success");
    }
}
