package com.smartcar.dororok.favorites.domain.req;

import lombok.Getter;

@Getter
public class DeletePlaceReq {
    private String contentId;

    public DeletePlaceReq(String contentId) {
        this.contentId = contentId;
    }

    public DeletePlaceReq() {
    }
}
