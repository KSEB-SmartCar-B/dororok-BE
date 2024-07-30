package com.smartcar.dororok.search.controller;

import com.smartcar.dororok.search.domain.SearchLogsDto;
import com.smartcar.dororok.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@Tag(name = "Search Controller", description = "검색 관련 API")
public class SearchController {

    private final SearchService searchService;

    @PostMapping
    @Operation(summary = "최근 검색어 추가", description = "최근 검색어 목록에 추가하는 API")
    public void addSearchLog(@RequestParam String searchLog) {
        searchService.addSearchLog(searchLog);
    }

    @GetMapping
    @Operation(summary = "최근 검색어 조회", description = "최근 검색어 목록에 조회하는 API (최대 20개)")
    public ResponseEntity<SearchLogsDto> getRecentSearchLogs() {
        return ResponseEntity.ok(SearchLogsDto.builder()
                .searchLogs(searchService.getRecentSearchLogs())
                .build());
    }

    @DeleteMapping
    @Operation(summary = "최근 검색어 삭제", description = "최근 검색어 목록에서 삭제하는 API")
    public void deleteSearchLog(@RequestParam String searchLog) {
        searchService.deleteSearchLog(searchLog);
    }
}
