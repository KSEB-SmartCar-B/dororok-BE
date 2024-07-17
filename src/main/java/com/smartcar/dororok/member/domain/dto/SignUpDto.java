package com.smartcar.dororok.member.domain.dto;

import com.smartcar.dororok.member.domain.entitiy.Gender;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SignUpDto {

    private String kakaoAccessToken;

    private String nickname;

    private Gender gender;

    private LocalDateTime birthday;

    private Boolean privacyAgreement;

    private Boolean locationInfoAgreement;

    private List<FavoriteGenreDto> favoriteGenreLists;

   // private Set<Genre> favoriteGenres;
}
