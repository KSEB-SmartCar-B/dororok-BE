package com.smartcar.dororok.recommendation.controller;

import com.smartcar.dororok.recommendation.domain.MusicMode;
import com.smartcar.dororok.recommendation.domain.dto.MusicRecommendationDto;
import com.smartcar.dororok.recommendation.domain.res.MusicRecommendationRes;
import com.smartcar.dororok.recommendation.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Recommendation Controller", description = "추천 관련 API")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/music")
    @Operation(summary = "노래 추천", description = "현재 장소, 날씨, 선호장르, 음악 모드 기반 노래 추천하는 API, MUSIC MODE는 밑에 참고해서 한글로 보내주면 됌!")
    public ResponseEntity<MusicRecommendationRes> getMusicRecommendation(@RequestParam String lat, @RequestParam String lng, @RequestParam MusicMode musicMode) {
        return ResponseEntity.ok(MusicRecommendationRes.builder()
                .lists(recommendationService.getMusicRecommendations(lat, lng, musicMode))
                .build());
    }
}
