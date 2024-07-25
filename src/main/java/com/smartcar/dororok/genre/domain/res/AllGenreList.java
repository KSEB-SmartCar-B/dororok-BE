package com.smartcar.dororok.genre.domain.res;

import com.smartcar.dororok.genre.domain.dto.GenreDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AllGenreList {
    private List<GenreDto> names;

    public AllGenreList() {
    }

    public AllGenreList(List<GenreDto> names) {
        this.names = names;
    }
}
