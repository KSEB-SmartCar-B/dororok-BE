package com.smartcar.dororok.recommendation.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicRecommendationDto {

    @JsonProperty(value = "TRACK_URI")
    private String trackURI;

    @JsonProperty(value = "PLAY_TIME")
    private Long playTime;
}
