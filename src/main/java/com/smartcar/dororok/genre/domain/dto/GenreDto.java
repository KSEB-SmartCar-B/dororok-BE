package com.smartcar.dororok.genre.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenreDto {
    private String name;

    public GenreDto() {
    }

    public GenreDto(String name) {
        this.name = name;
    }
}
