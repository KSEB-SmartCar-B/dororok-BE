package com.smartcar.dororok.member.repository;

import com.smartcar.dororok.member.domain.entitiy.FavoriteGenres;
import com.smartcar.dororok.member.domain.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteGenresRepository extends JpaRepository<FavoriteGenres, Long> {
    @Query("SELECT fg.genre.name FROM FavoriteGenres fg WHERE fg.member.id = :memberId")
    List<String> findGenreNamesByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT fg.genre.id FROM FavoriteGenres fg WHERE fg.member.id = :memberId")
    List<String> findGenreIdsByMemberId(@Param("memberId") Long memberId);

    void deleteByMember(Member member);

}
