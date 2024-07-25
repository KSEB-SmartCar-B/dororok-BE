package com.smartcar.dororok.recommendation.domain.res;

import com.smartcar.dororok.recommendation.domain.dto.MusicRecommendationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MusicRecommendationRes {
    private List<MusicRecommendationDto> lists;

    public MusicRecommendationRes() {
    }

    public MusicRecommendationRes(List<MusicRecommendationDto> lists) {
        this.lists = lists;
    }
}

