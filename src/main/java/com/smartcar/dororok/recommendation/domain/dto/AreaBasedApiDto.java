package com.smartcar.dororok.recommendation.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AreaBasedApiDto {
    private String areaCode;
    private String sigunguCode;
    private String cat1;
    private String cat2;
    private String cat3;
}
