package com.smartcar.dororok.member.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class KakaoInfoDto {
    private Long id;
    private String profileImageUrl;


    public KakaoInfoDto(Map<String, Object> attributes) {
        this.id = Long.valueOf(attributes.get("id").toString());
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if(kakaoAccount != null) {
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            if(profile != null) {
                this.profileImageUrl = profile.get("profile_image_url").toString();
            } else {
                this.profileImageUrl = null;
            }
        } else {
            this.profileImageUrl = null;
        }
    }
}