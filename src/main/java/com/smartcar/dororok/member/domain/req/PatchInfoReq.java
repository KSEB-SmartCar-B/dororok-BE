package com.smartcar.dororok.member.domain.req;

import com.smartcar.dororok.member.domain.dto.FavoriteGenreDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PatchInfoReq {

    private List<FavoriteGenreDto> favoriteGenres;

    public PatchInfoReq() {
    }

    public PatchInfoReq(List<FavoriteGenreDto> favoriteGenres) {
        this.favoriteGenres = favoriteGenres;
    }


}
