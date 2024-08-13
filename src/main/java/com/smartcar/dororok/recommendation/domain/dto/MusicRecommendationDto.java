package com.smartcar.dororok.recommendation.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicRecommendationDto {

    private String title;

    private String artist;

    @JsonProperty(value = "track_id")
    private String trackID;


    @JsonProperty(value = "TRACK_IMAGE")
    private String trackImage;
}
