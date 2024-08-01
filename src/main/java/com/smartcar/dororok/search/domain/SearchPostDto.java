package com.smartcar.dororok.search.domain;

import lombok.Getter;

@Getter
public class SearchPostDto {
    private String searchLog;

    public SearchPostDto() {
    }

    public SearchPostDto(String searchLog) {
        this.searchLog = searchLog;
    }
}
