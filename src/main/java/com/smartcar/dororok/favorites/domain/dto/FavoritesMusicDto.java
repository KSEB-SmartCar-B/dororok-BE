package com.smartcar.dororok.favorites.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesMusicDto {
    private String trackId;
    private String title;
    private String artist;
    private String imageUrl;
}
