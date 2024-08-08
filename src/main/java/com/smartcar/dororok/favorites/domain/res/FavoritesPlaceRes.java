package com.smartcar.dororok.favorites.domain.res;

import com.smartcar.dororok.favorites.domain.dto.FavoritesPlaceDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FavoritesPlaceRes {
   private List<FavoritesPlaceDto> favoritesPlaceList;

    public FavoritesPlaceRes(List<FavoritesPlaceDto> favoritesPlaceList) {
        this.favoritesPlaceList = favoritesPlaceList;
    }

    public FavoritesPlaceRes() {
    }
}
