package com.smartcar.dororok.recommendation.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceDetailDto {
    private String title;
    private String imageUrl;
    private String address;

    private String lat;
    private String lng;

    //대분류
    private String cat1;
    //중분류
    private String cat2;
    //소분류
    private String cat3;
}
