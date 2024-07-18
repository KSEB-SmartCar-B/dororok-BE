package com.smartcar.dororok.weather.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SkyCondition {
    CLEAR(1, "맑음"),
    PARTLY_CLOUDY(3, "구름많음"),
    CLOUDY(4, "흐림");

    private final int code;
    private final String description;

    SkyCondition(int code, String description) {
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

    public static SkyCondition fromCode(int code) {
        for (SkyCondition condition : SkyCondition.values()) {
            if (condition.code == code) {
                return condition;
            }
        }
        throw new IllegalArgumentException("Unknown SkyCondition code: " + code);
    }
}
