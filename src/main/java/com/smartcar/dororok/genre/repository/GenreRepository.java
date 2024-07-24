package com.smartcar.dororok.genre.repository;

import com.smartcar.dororok.genre.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("SELECT g.id FROM Genre g WHERE g.name = :name")
    Long findIdByName(@Param("name") String name);

    @Query("SELECT g.name FROM Genre g")
    List<String> findAllGenreNames();

    Genre findByName(String name);
}
