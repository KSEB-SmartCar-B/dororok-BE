package com.smartcar.dororok.member.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoriteGenreDto {
    private String name;

    public FavoriteGenreDto(String name) {
        this.name = name;
    }

    public FavoriteGenreDto() {
    }
}
