package com.smartcar.dororok.recommendation.domain.res;

import com.smartcar.dororok.recommendation.domain.dto.PlaceRecommendationDto;
import lombok.Getter;

import java.util.List;

@Getter
public class PlaceRecommendationRes {
    List<PlaceRecommendationDto> places;

    public PlaceRecommendationRes() {
    }

    public PlaceRecommendationRes(List<PlaceRecommendationDto> places) {
        this.places = places;
    }
}
