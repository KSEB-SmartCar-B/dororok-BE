package com.smartcar.dororok.recommendation.domain.req;

import com.smartcar.dororok.destination.domain.AgeRange;
import com.smartcar.dororok.member.domain.entitiy.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceRecommendationReq {
    private Gender gender;
    private AgeRange ageRange;
}
