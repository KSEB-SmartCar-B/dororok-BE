package com.smartcar.dororok.favorites.domain.res;

import com.smartcar.dororok.favorites.domain.dto.FavoritesMusicDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FavoritesMusicRes {
    List<FavoritesMusicDto> favoritesMusicList;

    public FavoritesMusicRes() {
    }

    public FavoritesMusicRes(List<FavoritesMusicDto> favoritesMusicList) {
        this.favoritesMusicList = favoritesMusicList;
    }
}
