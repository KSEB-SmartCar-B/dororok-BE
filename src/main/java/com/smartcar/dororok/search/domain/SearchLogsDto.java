package com.smartcar.dororok.search.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchLogsDto {

    private List<String> searchLogs;

    public SearchLogsDto() {
    }

    public SearchLogsDto(List<String> searchLogs) {
        this.searchLogs = searchLogs;
    }
}
