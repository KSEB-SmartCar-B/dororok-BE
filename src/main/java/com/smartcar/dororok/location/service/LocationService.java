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

    public LocationDto getAddressFromCoordinates(String x, String y) {
        Map<String, Object> locationAttributesByCoordinates = getAddressFromCoordinatesAPI(x, y);
        List<Map<String, Object>> documents = (List<Map<String, Object>>) locationAttributesByCoordinates.get("documents");
        if (documents == null || documents.isEmpty()) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        LocationInfoDto locationInfoDto = new LocationInfoDto(documents.get(0));
        LocationDto locationDto = LocationDto.builder()
                .cityName(locationInfoDto.getCityName())
                .provinceName(locationInfoDto.getProvinceName())
                .build();

        return locationDto;
    }
}
