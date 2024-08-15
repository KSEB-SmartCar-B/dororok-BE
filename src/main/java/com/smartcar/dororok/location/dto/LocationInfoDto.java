package com.smartcar.dororok.location.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
public class LocationInfoDto {

    private String addressName;
    private String region1depthName;
    private String region2depthName;
    private String region3depthName;

    public LocationInfoDto(Map<String, Object> document) {
        Map<String, Object> address = (Map<String, Object>) document.get("address");


        this.addressName = (String) address.get("address_name");
        this.region1depthName = (String) address.get("region_1depth_name");
        this.region2depthName = (String) address.get("region_2depth_name");
        this.region3depthName = (String) address.get("region_3depth_name");
    }

    public LocationInfoDto() {
        this.addressName = "제공되지 않는 주소입니다.";
        this.region1depthName ="제공되지 않는 주소입니다.";
        this.region2depthName ="제공되지 않는 주소입니다.";
        this.region3depthName ="제공되지 않는 주소입니다.";
    }
}
