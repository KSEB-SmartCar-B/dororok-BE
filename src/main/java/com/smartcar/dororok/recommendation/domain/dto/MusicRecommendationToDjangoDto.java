package com.smartcar.dororok.recommendation.domain.dto;

import com.smartcar.dororok.musicmode.domain.MusicMode;
import com.smartcar.dororok.weather.domain.PrecipitationType;
import com.smartcar.dororok.weather.domain.SkyCondition;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MusicRecommendationToDjangoDto { //프론트에서 요청받아서 장고서버로 보낼때 사용하는 dto

    private List<String> genres;
    private String memberId;
    private String lat;
    private String lng;
    private String region1depthName;//도 (광역시는 여기에 ~시)
    private String region2depthName;//시 (광역시는 여기에 ~구)
    private String region3depthName;
    private SkyCondition skyCondition; //하늘 상태
    private PrecipitationType precipitationType; //강수 상태
    private MusicMode musicMode; //선택한 음악 모드

}
