package com.smartcar.dororok.musiclisteninglog.domain.dto;

import lombok.Getter;

@Getter
public class MusicDto {
    private String trackId;

    public MusicDto() {
    }

    public MusicDto(String trackId) {
        this.trackId = trackId;
    }
}
