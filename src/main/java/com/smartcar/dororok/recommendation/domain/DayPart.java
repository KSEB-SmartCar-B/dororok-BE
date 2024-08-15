package com.smartcar.dororok.recommendation.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DayPart {
    MORNING("6to12"),
    AFTERNOON("12to18"),
    EVENING("18to24"),
    DAYBREAK("24to6");

    private final String description;

    DayPart(String description) {this.description = description;}

    @JsonValue
    public String getDescription() {
        return description;
    }
}
