package com.smartcar.dororok.favorites.repository;

import com.smartcar.dororok.favorites.domain.entity.FavoritesMusic;
import com.smartcar.dororok.member.domain.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FavoritesMusicRepository extends JpaRepository<FavoritesMusic, Long> {

    List<FavoritesMusic> findByMemberId(Long memberId);

    @Transactional
    void deleteByMemberIdAndTrackId(Long memberId, String trackId);

    @Transactional
    void deleteAllByMember(Member member);

    Optional<FavoritesMusic> findByMemberIdAndTrackId(Long memberId, String trackId);

}
