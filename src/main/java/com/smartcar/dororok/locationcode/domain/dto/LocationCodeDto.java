package com.smartcar.dororok.locationcode.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationCodeDto {
    private Integer areaCode;
    private Integer sigunguCode;
}
