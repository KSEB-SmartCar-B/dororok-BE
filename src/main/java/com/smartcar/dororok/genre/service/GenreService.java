package com.smartcar.dororok.genre.service;

import com.smartcar.dororok.genre.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<String> findAllGenreNames() {
        return genreRepository.findAllGenreNames();
    }
}
