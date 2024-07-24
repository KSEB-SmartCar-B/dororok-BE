package com.smartcar.dororok.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LocationDto {
    private String provinceName;
    private String cityName;

}
