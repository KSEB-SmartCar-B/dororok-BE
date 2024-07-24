package com.smartcar.dororok.genre.controller;

import com.smartcar.dororok.genre.repository.GenreRepository;
import com.smartcar.dororok.genre.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/name-list")
    public ResponseEntity<List<String>> nameList() {
        return ResponseEntity.ok(genreService.findAllGenreNames());
    }

}
