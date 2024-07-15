package com.smartcar.dororok.member.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KakaoAccessTokenDto {
    public KakaoAccessTokenDto(String kakaoAccessToken) {
        this.kakaoAccessToken = kakaoAccessToken;
    }

    public KakaoAccessTokenDto() {}


    private String kakaoAccessToken;
}
