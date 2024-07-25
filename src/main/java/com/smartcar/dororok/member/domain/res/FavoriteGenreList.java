package com.smartcar.dororok.member.domain.res;

import com.smartcar.dororok.member.domain.dto.FavoriteGenreDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FavoriteGenreList {
    private List<FavoriteGenreDto> favoriteGenres;

    public FavoriteGenreList() {
    }

    public FavoriteGenreList(List<FavoriteGenreDto> favoriteGenres) {
        this.favoriteGenres = favoriteGenres;
    }
}
