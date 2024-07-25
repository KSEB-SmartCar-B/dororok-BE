package com.smartcar.dororok.member.domain.dto;

import com.smartcar.dororok.member.domain.entitiy.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PatchInfoDto {
    private String nickname;
    private LocalDate birthday;
    private Gender gender;
}
