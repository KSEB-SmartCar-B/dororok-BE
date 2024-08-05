package com.smartcar.dororok.recommendation.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceDetailDto {
    private String title;
    private String imageUrl;
    private String address;
    private String description;
}
