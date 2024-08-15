package com.smartcar.dororok.location.service;

import com.smartcar.dororok.global.exception.CustomException;
import com.smartcar.dororok.global.exception.ErrorCode;
import com.smartcar.dororok.location.dto.CoordinatesDto;
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

    public Map<String, Object> getCoordinatesFromKeywordAPI(String keyword) {
        return WebClient.create("https://dapi.kakao.com")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/address.json")
                        .queryParam("query", keyword)
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
            return new LocationInfoDto();
        }
        LocationInfoDto locationInfoDto = new LocationInfoDto(documents.get(0));


        return locationInfoDto;
    }

    public CoordinatesDto getCoordinatesFromAddress(String address) {
        Map<String, Object> response = getCoordinatesFromKeywordAPI(address);
        List<Map<String, Object>> documents = (List<Map<String, Object>>) response.get("documents");

        CoordinatesDto coordinatesDto = new CoordinatesDto(documents.get(0));

        return coordinatesDto;
    }
}
