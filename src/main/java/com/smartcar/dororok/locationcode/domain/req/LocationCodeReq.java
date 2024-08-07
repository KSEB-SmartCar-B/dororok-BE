package com.smartcar.dororok.locationcode.domain.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationCodeReq {
    private String region1depthName;
    private String region2depthName;
}
