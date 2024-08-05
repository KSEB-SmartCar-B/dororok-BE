package com.smartcar.dororok.recommendation.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceRecommendationDto {
    private String contentId;

    private String region1depthName;
    private String region2depthName;
    private String region3depthName;

    private String imageUrl;

    private String title;
}
