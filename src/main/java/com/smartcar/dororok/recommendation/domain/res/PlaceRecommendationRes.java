package com.smartcar.dororok.recommendation.domain.res;

import com.smartcar.dororok.recommendation.domain.dto.PlaceInfoDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaceRecommendationRes {
    private List<PlaceInfoDto> places;

    private Integer pageNumbers;

}
