package com.smartcar.dororok.favorites.domain.req;

import lombok.Getter;

import java.util.List;

@Getter
public class DeletePlaceListReq {
    List<String> contentIds;
    public DeletePlaceListReq(List<String> contentIds) {
        this.contentIds = contentIds;
    }

    public DeletePlaceListReq() {
    }
}
