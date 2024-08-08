package com.smartcar.dororok.musicmode.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MusicModeRes {
    List<MusicModeDto> musicModeList;
}
