package com.smartcar.dororok.music.domain.dto;

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
