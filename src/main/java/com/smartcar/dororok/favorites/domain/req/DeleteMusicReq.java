package com.smartcar.dororok.favorites.domain.req;

import lombok.Getter;

@Getter
public class DeleteMusicReq {
    private String trackId;

    public DeleteMusicReq(String trackId) {
        this.trackId = trackId;
    }

    public DeleteMusicReq() {
    }
}
