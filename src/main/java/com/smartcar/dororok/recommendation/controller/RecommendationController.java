package com.smartcar.dororok.recommendation.controller;

import com.smartcar.dororok.recommendation.domain.MusicMode;
import com.smartcar.dororok.recommendation.domain.dto.PlaceDetailDto;
import com.smartcar.dororok.recommendation.domain.res.MusicRecommendationRes;
import com.smartcar.dororok.recommendation.domain.res.PlaceRecommendationRes;
import com.smartcar.dororok.recommendation.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "내 주변 관광지 추천", description = "현재 위도, 경도 넣으면 TourAPI에서 제공하는 5개 관광지 정보 제공")
    @GetMapping("/places/nearby")
    public ResponseEntity<PlaceRecommendationRes> getNearByPlacesRecommendation(@RequestParam String lat, @RequestParam String lng) {
        return ResponseEntity.ok(new PlaceRecommendationRes(recommendationService.getNearByPlacesRecommendation(lat, lng)));
    }

    @Operation(summary = "관광지 추천", description = "장고에서 추천 알고리즘 돌려 지역 선정하고, TourAPI에서 제공하는 5개 관광지 정보 제공")
    @GetMapping("/places")
    public ResponseEntity<PlaceRecommendationRes> getPlacesRecommendation() {
        return ResponseEntity.ok(new PlaceRecommendationRes(recommendationService.getPlacesRecommendation()));
    }

    @Operation(summary = "추천 관광지 상세 정보", description = "관광지의 contentId 전달하면 관광지의 상세 정보 제공")
    @GetMapping("/place/detail")
    public ResponseEntity<PlaceDetailDto> getPlaceDetail(@RequestParam String contentId) {
        return ResponseEntity.ok(recommendationService.getPlaceDetail(contentId));
    }

    @Operation(summary = "근처 관광지 상세 정보", description = "관광지의 contentId 전달하면 관광지의 상세 정보 제공")
    @GetMapping("/place/nearby/detail")
    public ResponseEntity<PlaceDetailDto> getNearByPlaceDetail(@RequestParam String contentId) {
        return ResponseEntity.ok(recommendationService.getNeraByPlaceDetail(contentId));
    }
}
