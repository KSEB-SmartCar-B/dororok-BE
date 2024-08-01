package com.smartcar.dororok.destination.domain.entity;

import com.smartcar.dororok.member.domain.entitiy.Gender;
import com.smartcar.dororok.destination.domain.AgeRange;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Destination {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AgeRange ageRange;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String region1depthName;

    private String region2depthName;

    private String region3depthName;

    public Destination(Long id, AgeRange ageRange, Gender gender, String region1depthName, String region2depthName, String region3depthName) {
        this.id = id;
        this.ageRange = ageRange;
        this.gender = gender;
        this.region1depthName = region1depthName;
        this.region2depthName = region2depthName;
        this.region3depthName = region3depthName;
    }
}
