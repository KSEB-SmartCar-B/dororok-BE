package com.smartcar.dororok.musicmode.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MusicMode {
    DAILY( "일상"),
    TO_WORK("출근"),
    LEAVE_WORK("퇴근"),
    TRAVEL("여행"),
    DRIVE("드라이브"),
    DOROROK("도로록 Pick!"),
    WITH_LOVER("데이트"),
    WITH_FRIENDS("친구들과");

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
