package com.smartcar.dororok.recommendation.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MusicMode {
    DAILY( "일상"),
    GO_TO_WORK("출근"),
    GET_OFF_WORK("퇴근"),
    TRAVEL("여행"),
    DRIVE("드라이브"),
    DOROROK_PICK("도로록 Pick!");

    private final String description;

    MusicMode(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static MusicMode from(String input) {
        for (MusicMode status : MusicMode.values()) {
            if (status.getDescription().equals(input)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown Music Mode: " + input);
    }
}