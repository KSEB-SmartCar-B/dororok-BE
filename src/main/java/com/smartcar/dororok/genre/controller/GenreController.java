package com.smartcar.dororok.genre.controller;

import com.smartcar.dororok.genre.domain.res.AllGenreList;
import com.smartcar.dororok.genre.repository.GenreRepository;
import com.smartcar.dororok.genre.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
@Tag(name = "Genre Controller", description = "장르 관련 API")
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/name-list")
    @Operation(summary = "모든 장르 이름, 이미지 경로 리스트", description = "모든 장르 이름, 이미지 경로 리스트 반환하는 API")
    public ResponseEntity<AllGenreList> nameList() {
        return ResponseEntity.ok(AllGenreList.builder()
                .names(genreService.findAllGenreNames())
                .build());
    }

}
