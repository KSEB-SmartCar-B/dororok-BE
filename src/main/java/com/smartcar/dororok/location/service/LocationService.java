package com.smartcar.dororok.location.service;

import com.smartcar.dororok.global.exception.CustomException;
import com.smartcar.dororok.global.exception.ErrorCode;
import com.smartcar.dororok.location.dto.LocationDto;
import com.smartcar.dororok.location.dto.LocationInfoDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class LocationService {
    private String key = System.getenv("KAKAO_REST_API_KEY");


    public Map<String, Object> getAddressFromCoordinatesAPI(String lat, String lng) {
        return WebClient.create("https://dapi.kakao.com")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/geo/coord2address.json")
                        .queryParam("x", lng)
                        .queryParam("y", lat)
                        .build())
                .header("Authorization", "KakaoAK " + key)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

    public LocationInfoDto getAddressFromCoordinates(String lat, String lng) {
        Map<String, Object> locationAttributesByCoordinates = getAddressFromCoordinatesAPI(lat, lng);
        List<Map<String, Object>> documents = (List<Map<String, Object>>) locationAttributesByCoordinates.get("documents");
        if (documents == null || documents.isEmpty()) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        LocationInfoDto locationInfoDto = new LocationInfoDto(documents.get(0));


        return locationInfoDto;
    }

    public LocationDto getProvinceAndCityName(LocationInfoDto dto) {
        return LocationDto.builder()
                .provinceName(dto.getRegion1depthName())
                .cityName(dto.getRegion2depthName())
                .build();
    }
}
