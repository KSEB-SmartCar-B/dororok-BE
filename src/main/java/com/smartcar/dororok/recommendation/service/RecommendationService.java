package com.smartcar.dororok.recommendation.service;

import com.smartcar.dororok.destination.domain.AgeRange;
import com.smartcar.dororok.destination.service.DestinationService;
import com.smartcar.dororok.global.auth.utils.SecurityUtils;
import com.smartcar.dororok.location.dto.CoordinatesDto;
import com.smartcar.dororok.location.dto.LocationInfoDto;
import com.smartcar.dororok.location.service.LocationService;
import com.smartcar.dororok.member.domain.entitiy.Gender;
import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.member.repository.FavoriteGenresRepository;
import com.smartcar.dororok.member.repository.MemberRepository;
import com.smartcar.dororok.recommendation.domain.MusicMode;
import com.smartcar.dororok.recommendation.domain.dto.MusicRecommendationDto;
import com.smartcar.dororok.recommendation.domain.dto.MusicRecommendationToDjangoDto;
import com.smartcar.dororok.recommendation.domain.dto.PlaceDetailDto;
import com.smartcar.dororok.recommendation.domain.dto.PlaceRecommendationDto;
import com.smartcar.dororok.weather.domain.GetWeatherDto;
import com.smartcar.dororok.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RecommendationService {

    private final String key = System.getenv("OPENAPI_KEY");
    private final String TourAPIURL = "http://apis.data.go.kr/B551011/KorService1";

    private final MemberRepository memberRepository;
    private final FavoriteGenresRepository favoriteGenresRepository;
    private final WeatherService weatherService;
    private final LocationService locationService;
    private final DestinationService destinationService;

    public List<MusicRecommendationDto> getMusicRecommendations(String lat, String lng, MusicMode musicMode) {

        GetWeatherDto weather = weatherService.getCurrentWeather(lat,lng);

        LocationInfoDto location = locationService.getAddressFromCoordinates(lat, lng);

        Member member = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);

        List<String> genres = favoriteGenresRepository.findGenreNamesByMemberId(member.getId());

        MusicRecommendationToDjangoDto dto = MusicRecommendationToDjangoDto.builder()
                .genres(genres)
                .memberId(SecurityUtils.getCurrentUsername())
                .lat(lat)
                .lng(lng)
                .region1depthName(location.getRegion1depthName())
                .region2depthName(location.getRegion2depthName())
                .region3depthName(location.getRegion3depthName())
                .skyCondition(weather.getSkyCondition())
                .precipitationType(weather.getPrecipitationType())
                .musicMode(musicMode)
                .build();

        log.info("getMusicRecommendations: {}", dto);
        //장고 서버에 dto 보내서 MusicRecommendationDto 결과 받기
        return null;
    }

    public List<PlaceRecommendationDto> getPlacesRecommendation() {
        Member member = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        AgeRange ageRange = destinationService.getAgeRange(member.getBirthday());
        Gender gender = member.getGender();

        //장고 서버에 dto 보내서 지역 정보 받기

        String address = "";// region1 + " " + region2 + " " + region3;
        CoordinatesDto coordinates = locationService.getCoordinatesFromAddress(address);

        return getNearByPlacesRecommendation(coordinates.getY(), coordinates.getX());
    }

    //현재 위도, 경도 넣었을때 위치기반 관광정보조회 API 호출하여 DTO에 저장하는 코드
    public List<PlaceRecommendationDto> getNearByPlacesRecommendation(String lat, String lng) {
        Map<String, Object> res = getPlacesInfoFromAPI(lat, lng);
        Map<String, Object> response = (Map<String, Object>) res.get("response");
        Map<String, Object> body = (Map<String, Object>) response.get("body");
        Map<String, Object> items = (Map<String, Object>) body.get("items");
        List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");
        List<PlaceRecommendationDto> result = new ArrayList<>();
        for (Map<String, Object> place : item) {
            LocationInfoDto location = locationService.getAddressFromCoordinates((String)place.get("mapy") , (String)place.get("mapx"));
            PlaceRecommendationDto placeRecommendationDto = PlaceRecommendationDto.builder()
                    .region1depthName(location.getRegion1depthName())
                    .region2depthName(location.getRegion2depthName())
                    .region3depthName(location.getRegion3depthName())
                    .contentId((String)place.get("contentid"))
                    .imageUrl((String)place.get("firstimage"))
                    .title((String)place.get("title"))
                    .build();
            result.add(placeRecommendationDto);
        }

        return result;
    }

    public PlaceDetailDto getPlaceDetail(String contentId) {
        Map<String, Object> res = getPlaceDetailFromAPI(contentId);
        Map<String, Object> response = (Map<String, Object>) res.get("response");
        Map<String, Object> body = (Map<String, Object>) response.get("body");
        Map<String, Object> items = (Map<String, Object>) body.get("items");
        List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");

        Map<String,Object> detail = item.get(0);

        return PlaceDetailDto.builder()
                .title((String)detail.get("title"))
                .address((String)detail.get("addr1"))
                .imageUrl((String)detail.get("firstimage"))
                .build();
    }

    public PlaceDetailDto getNeraByPlaceDetail(String contentId) {
        Map<String, Object> res = getPlaceDetailFromAPI(contentId);
        Map<String, Object> response = (Map<String, Object>) res.get("response");
        Map<String, Object> body = (Map<String, Object>) response.get("body");
        Map<String, Object> items = (Map<String, Object>) body.get("items");
        List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");

        Map<String,Object> detail = item.get(0);

        PlaceDetailDto dto = PlaceDetailDto.builder()
                .title((String)detail.get("title"))
                .lat((String)detail.get("mapy"))
                .lng((String)detail.get("mapx"))
                .cat1((String)detail.get("cat1"))
                .cat2((String)detail.get("cat2"))
                .cat3((String)detail.get("cat3"))
                .build();


        return PlaceDetailDto.builder()
                .title((String)detail.get("title"))
                .address((String)detail.get("addr1"))
                .imageUrl((String)detail.get("firstimage"))
                .build();
    }

    //위치기반 관광정보조회	API 호출하는 코드 (JSON을 Map에 저장)
    public Map<String, Object> getPlacesInfoFromAPI(String lat, String lng) {
        return WebClient.create(TourAPIURL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/locationBasedList1")
                        .queryParam("numOfRows", 5)
                        .queryParam("mapX", lng)
                        .queryParam("mapY", lat)
                        .queryParam("_type", "JSON")
                        .queryParam("serviceKey", key)
                        .queryParam("MobileOS", "AND")
                        .queryParam("MobileApp", "dororok")
                        .queryParam("radius", 20000)
                        .queryParam("contentTypeId", 12)
                        .queryParam("arrange","O")
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }


    //공통정보조회 API 호출하는 코드
    public Map<String, Object> getPlaceDetailFromAPI(String contentId) {
        return WebClient.create(TourAPIURL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/detailCommon1")
                        .queryParam("_type", "JSON")
                        .queryParam("serviceKey", key)
                        .queryParam("MobileOS", "AND")
                        .queryParam("MobileApp", "dororok")
                        .queryParam("contentId", contentId)
                        .queryParam("catcodeYN", "Y")
                        .queryParam("defaultYN", "Y")
                        .queryParam("addrinfoYN", "Y")
                        .queryParam("firstImageYN", "Y")
                        .queryParam("areacodeYN","Y")
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

    //지역코드조회 API 호출하는 코드
    public Map<String, Object> getPlaceAreaCodeFromAPI(Integer areaCode) {
        return WebClient.create(TourAPIURL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/areaCode1")
                        .queryParam("numOfRows", 100)
                        .queryParam("_type", "JSON")
                        .queryParam("serviceKey", key)
                        .queryParam("MobileOS", "AND")
                        .queryParam("MobileApp", "dororok")
                        .queryParam("areaCode", areaCode)
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }


}
