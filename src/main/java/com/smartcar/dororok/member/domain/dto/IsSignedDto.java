package com.smartcar.dororok.member.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IsSignedDto {
    public IsSignedDto(Boolean isSinged) {
        this.isSigned = isSinged;
    }

    public IsSignedDto() {
    }

    private Boolean isSigned;
}
