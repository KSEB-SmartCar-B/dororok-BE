package com.smartcar.dororok.member.repository;

import com.smartcar.dororok.member.domain.entitiy.FavoriteGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteGenresRepository extends JpaRepository<FavoriteGenres, Long> {

}
