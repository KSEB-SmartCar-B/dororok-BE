package com.smartcar.dororok.location.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class LocationInfoDto {

    private String addressName;
    private String provinceName;
    private String cityName;

    public LocationInfoDto(Map<String, Object> document) {
        Map<String, Object> address = (Map<String, Object>) document.get("address");


        this.addressName = (String) address.get("address_name");
        this.provinceName = (String) address.get("region_1depth_name");
        this.cityName = (String) address.get("region_2depth_name");
    }
}
