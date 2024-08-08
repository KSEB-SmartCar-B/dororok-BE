package com.smartcar.dororok.favorites.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesPlaceDto {
    private String title;
    private String address;
    private String imageUrl;
    private String contentId;
}
