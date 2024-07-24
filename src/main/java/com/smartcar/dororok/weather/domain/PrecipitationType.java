package com.smartcar.dororok.weather.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PrecipitationType {
    NONE(0, "없음"),
    RAIN(1, "비"),
    RAIN_SNOW(2, "비"),
    SNOW(3, "눈"),
    SHOWER(4, "소나기");

    private final int code;
    private final String description;

    PrecipitationType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static PrecipitationType fromCode(int code) {
        for (PrecipitationType type : PrecipitationType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown PrecipitationType code: " + code);
    }
}
