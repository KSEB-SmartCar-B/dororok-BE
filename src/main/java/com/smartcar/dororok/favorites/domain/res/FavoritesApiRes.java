package com.smartcar.dororok.favorites.domain.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesApiRes {
    private String response;

    public FavoritesApiRes() {
    }

    public FavoritesApiRes(String response) {
        this.response = response;
    }
}
