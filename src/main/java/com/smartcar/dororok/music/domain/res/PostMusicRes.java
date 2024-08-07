package com.smartcar.dororok.music.domain.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostMusicRes {
    private String response;

    public PostMusicRes() {
    }
    public PostMusicRes(String response) {
        this.response = response;
    }
}
