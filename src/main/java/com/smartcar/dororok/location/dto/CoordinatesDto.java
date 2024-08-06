package com.smartcar.dororok.location.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class CoordinatesDto {
    private String x;
    private String y;

    public CoordinatesDto(Map<String, Object> document) {
        Map<String, Object> address = (Map<String, Object>) document.get("address");
        this.x = (String) address.get("x");
        this.y = (String) address.get("y");
    }
}
