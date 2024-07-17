package com.smartcar.dororok.member.repository;

import com.smartcar.dororok.member.domain.entitiy.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("SELECT g.id FROM Genre g WHERE g.name = :name")
    Long findIdByName(@Param("name") String name);

    Genre findByName(String name);
}
