package com.smartcar.dororok.member.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccessTokenDto {
    public AccessTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessTokenDto() {
    }

    private String accessToken;
}
