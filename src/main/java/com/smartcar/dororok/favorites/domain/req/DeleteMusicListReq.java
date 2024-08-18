package com.smartcar.dororok.favorites.domain.req;

import lombok.Getter;

import java.util.List;


@Getter
public class DeleteMusicListReq {
    List<String> trackIds;
    public DeleteMusicListReq(List<String> trackIds) {
        this.trackIds = trackIds;
    }

    public DeleteMusicListReq() {
    }
}
