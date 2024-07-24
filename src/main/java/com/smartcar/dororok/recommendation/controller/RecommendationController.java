package com.smartcar.dororok.recommendation.controller;

import com.smartcar.dororok.recommendation.entity.MusicMode;
import com.smartcar.dororok.recommendation.entity.dto.MusicRecommendationDto;
import com.smartcar.dororok.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/music")
    public ResponseEntity<List<MusicRecommendationDto>> getMusicRecommendation(@RequestParam String lat, @RequestParam String lng, @RequestParam MusicMode musicMode) {
        return ResponseEntity.ok(recommendationService.getMusicRecommendations(lat, lng, musicMode));
    }
}
