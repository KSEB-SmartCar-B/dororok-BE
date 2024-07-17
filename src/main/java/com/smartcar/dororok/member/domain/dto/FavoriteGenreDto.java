package com.smartcar.dororok.member.domain.dto;

import lombok.Getter;

@Getter
public class FavoriteGenreDto {
    private String name;

    public FavoriteGenreDto(String name) {
        this.name = name;
    }

    public FavoriteGenreDto() {
    }
}
