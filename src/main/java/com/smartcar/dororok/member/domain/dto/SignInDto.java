package com.smartcar.dororok.member.domain.dto;

import com.smartcar.dororok.global.auth.dto.JwtToken;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignInDto {
    private JwtToken jwtToken;
    private Boolean isSigned;
}
