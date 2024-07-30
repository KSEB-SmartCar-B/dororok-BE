package com.smartcar.dororok.search.controller;

import com.smartcar.dororok.search.domain.SearchLogsDto;
import com.smartcar.dororok.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @PostMapping
    public void addSearchLog(@RequestParam String searchLog) {
        searchService.addSearchLog(searchLog);
    }

    @GetMapping
    public ResponseEntity<SearchLogsDto> getRecentSearchLogs() {
        return ResponseEntity.ok(SearchLogsDto.builder()
                .searchLogs(searchService.getRecentSearchLogs())
                .build());
    }

    @DeleteMapping
    public void deleteSearchLog(@RequestParam String searchLog) {
        searchService.deleteSearchLog(searchLog);
    }
}
