package com.smartcar.dororok.recommendation.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicRecommendationDto {

    @JsonProperty(value = "TRACK_URI")
    private String trackURI;

    @JsonProperty(value = "PLAY_TIME")
    private Long playTime;

    @JsonProperty(value = "TRACK_IMAGE")
    private String trackImage;
}
