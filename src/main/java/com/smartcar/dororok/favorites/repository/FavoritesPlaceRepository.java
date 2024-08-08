package com.smartcar.dororok.favorites.repository;

import com.smartcar.dororok.favorites.domain.entity.FavoritesMusic;
import com.smartcar.dororok.favorites.domain.entity.FavoritesPlace;
import com.smartcar.dororok.member.domain.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FavoritesPlaceRepository extends JpaRepository<FavoritesPlace, Long> {

    List<FavoritesPlace> findByMemberId(Long memberId);

    @Transactional
    void deleteByMemberIdAndContentId(Long memberId, String contentId);

    @Transactional
    void deleteAllByMember(Member member);

    Optional<FavoritesPlace> findByMemberIdAndContentId(Long memberId, String contentId);

}
