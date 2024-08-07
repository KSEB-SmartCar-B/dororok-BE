package com.smartcar.dororok.recommendation.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceDetailDto {
    private String contentId;

    private String address;
    private String imageUrl;

    private String title;
}
