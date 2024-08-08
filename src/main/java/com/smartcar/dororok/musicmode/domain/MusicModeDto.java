package com.smartcar.dororok.musicmode.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicModeDto {
    private String name;
    private String imageUrl;
}
