package com.smartcar.dororok.member.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class KakaoInfoDto {
    private Long id;
    private String email;


    public KakaoInfoDto(Map<String, Object> attributes) {
        this.id = Long.valueOf(attributes.get("id").toString());
        this.email = attributes.get("email") != null ? attributes.get("email").toString() : "";
    }
}