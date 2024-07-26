package com.smartcar.dororok.genre.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenreDto {
    private String name;
    private String imageUrl;
}
