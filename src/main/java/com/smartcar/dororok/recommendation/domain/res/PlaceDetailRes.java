package com.smartcar.dororok.recommendation.domain.res;

import com.smartcar.dororok.recommendation.domain.dto.PlaceDetailDto;
import com.smartcar.dororok.recommendation.domain.dto.PlaceInfoDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlaceDetailRes {
    private PlaceDetailDto placeDetail;
    private List<PlaceInfoDto> placeList;
}
