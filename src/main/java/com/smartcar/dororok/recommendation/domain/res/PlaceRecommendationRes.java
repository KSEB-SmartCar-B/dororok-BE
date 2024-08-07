package com.smartcar.dororok.recommendation.domain.res;

import com.smartcar.dororok.recommendation.domain.dto.PlaceInfoDto;
import lombok.Getter;

import java.util.List;

@Getter
public class PlaceRecommendationRes {
    List<PlaceInfoDto> places;

    public PlaceRecommendationRes() {
    }

    public PlaceRecommendationRes(List<PlaceInfoDto> places) {
        this.places = places;
    }
}
