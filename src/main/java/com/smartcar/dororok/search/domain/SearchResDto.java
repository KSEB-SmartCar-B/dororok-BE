package com.smartcar.dororok.search.domain;

import lombok.Getter;

@Getter
public class SearchResDto {

    private String response;

    public SearchResDto() {
    }

    public SearchResDto(String response) {
        this.response = response;
    }
}
