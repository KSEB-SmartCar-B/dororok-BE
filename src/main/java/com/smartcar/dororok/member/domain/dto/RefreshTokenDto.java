package com.smartcar.dororok.member.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenDto {
    public RefreshTokenDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshTokenDto() {
    }

    private String refreshToken;
}
