package com.smartcar.dororok.favorites.service;

import com.smartcar.dororok.favorites.domain.dto.FavoritesMusicDto;
import com.smartcar.dororok.favorites.domain.dto.FavoritesPlaceDto;
import com.smartcar.dororok.favorites.domain.entity.FavoritesMusic;
import com.smartcar.dororok.favorites.domain.entity.FavoritesPlace;
import com.smartcar.dororok.favorites.domain.res.ExistRes;
import com.smartcar.dororok.favorites.domain.res.FavoritesMusicRes;
import com.smartcar.dororok.favorites.domain.res.FavoritesPlaceRes;
import com.smartcar.dororok.favorites.domain.res.FavoritesApiRes;
import com.smartcar.dororok.favorites.repository.FavoritesMusicRepository;
import com.smartcar.dororok.favorites.repository.FavoritesPlaceRepository;
import com.smartcar.dororok.global.auth.utils.SecurityUtils;
import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoritesService {

    private final FavoritesMusicRepository favoritesMusicRepository;
    private final FavoritesPlaceRepository favoritesPlaceRepository;
    private final MemberRepository memberRepository;

    public FavoritesApiRes saveMusic(FavoritesMusicDto dto) {
        Member findMember = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        favoritesMusicRepository.save(FavoritesMusic.builder()
                .member(findMember)
                .artist(dto.getArtist())
                .title(dto.getTitle())
                .trackId(dto.getTrackId())
                .imageUrl(dto.getImageUrl()).build());
        return FavoritesApiRes.builder()
                .response("success")
                .build();
    }

    public FavoritesApiRes savePlace(FavoritesPlaceDto dto) {
        Member findMember = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        favoritesPlaceRepository.save(FavoritesPlace.builder()
                .member(findMember)
                .title(dto.getTitle())
                .address(dto.getAddress())
                .imageUrl(dto.getImageUrl())
                .contentId(dto.getContentId())
                .build());
        return FavoritesApiRes.builder()
                .response("success")
                .build();
    }

    public FavoritesMusicRes getFavoritesMusics() {
        Member findMember = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        List<FavoritesMusic> list = favoritesMusicRepository.findByMemberId(findMember.getId());
        List<FavoritesMusicDto> result = new ArrayList<>();
        for (FavoritesMusic favoritesMusic : list) {
            result.add(FavoritesMusicDto.builder()
                    .artist(favoritesMusic.getArtist())
                    .title(favoritesMusic.getTitle())
                    .trackId(favoritesMusic.getTrackId())
                    .imageUrl(favoritesMusic.getImageUrl())
                    .build());
        }
        return FavoritesMusicRes.builder()
                .favoritesMusicList(result)
                .build();
    }

    public FavoritesPlaceRes getFavoritesPlaces() {
        Member findMember = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        List<FavoritesPlace> list = favoritesPlaceRepository.findByMemberId(findMember.getId());
        List<FavoritesPlaceDto> result = new ArrayList<>();
        for (FavoritesPlace favoritesPlace : list) {
            result.add(FavoritesPlaceDto.builder()
                    .title(favoritesPlace.getTitle())
                    .address(favoritesPlace.getAddress())
                    .imageUrl(favoritesPlace.getImageUrl())
                    .contentId(favoritesPlace.getContentId())
                    .build());
        }
        return FavoritesPlaceRes.builder()
                .favoritesPlaceList(result)
                .build();
    }

    public FavoritesApiRes deleteFavoritesMusic(String trackId) {
        Member findMember = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        favoritesMusicRepository.deleteByMemberIdAndTrackId(findMember.getId(), trackId);
        return FavoritesApiRes.builder()
                .response("success")
                .build();
    }

    public FavoritesApiRes deleteFavoritesPlace(String contentId) {
        Member findMember = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        favoritesPlaceRepository.deleteByMemberIdAndContentId(findMember.getId(), contentId);
        return FavoritesApiRes.builder()
                .response("success")
                .build();
    }

    public void deleteFavoritesByMember(Member findMember) {
        favoritesMusicRepository.deleteAllByMember(findMember);
        favoritesPlaceRepository.deleteAllByMember(findMember);
    }

    public ExistRes isExistMusic(String trackId) {
        Member findMember = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        Optional<FavoritesMusic> favoritesMusic = favoritesMusicRepository.findByMemberIdAndTrackId(findMember.getId(), trackId);
        return ExistRes.builder()
                .isExisted(favoritesMusic.isPresent())
                .build();
    }

    public ExistRes isExistPlace(String contentId) {
        Member findMember = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        Optional<FavoritesPlace> favoritesPlace = favoritesPlaceRepository.findByMemberIdAndContentId(findMember.getId(), contentId);
        return ExistRes.builder()
                .isExisted(favoritesPlace.isPresent())
                .build();
    }

}
