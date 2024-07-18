package com.smartcar.dororok.weather.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetWeatherDto {
    private SkyCondition skyCondition;
    private PrecipitationType precipitationType;
}
