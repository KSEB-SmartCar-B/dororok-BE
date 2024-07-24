package com.smartcar.dororok.member.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurrentGenreDto {
    private String name;

    public CurrentGenreDto() {
    }

    public CurrentGenreDto(String name) {
        this.name = name;
    }
}
