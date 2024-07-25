package com.smartcar.dororok.genre.service;

import com.smartcar.dororok.genre.domain.Genre;
import com.smartcar.dororok.genre.domain.dto.GenreDto;
import com.smartcar.dororok.genre.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<GenreDto> findAllGenreNames() {
        List<Genre> genres = genreRepository.findAll();
        List<GenreDto> result = new ArrayList<>();
        for (Genre genre : genres) {
            result.add(GenreDto.builder()
                    .name(genre.getName())
                    .build());
        }
        return result;
    }
}
