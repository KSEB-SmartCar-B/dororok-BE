package com.smartcar.dororok.recommendation.service;

import com.smartcar.dororok.destination.domain.AgeRange;
import com.smartcar.dororok.destination.service.DestinationService;
import com.smartcar.dororok.global.auth.utils.SecurityUtils;
import com.smartcar.dororok.location.dto.LocationInfoDto;
import com.smartcar.dororok.location.service.LocationService;
import com.smartcar.dororok.locationcode.domain.LocationCode;
import com.smartcar.dororok.locationcode.domain.dto.LocationCodeDto;
import com.smartcar.dororok.locationcode.domain.req.LocationCodeReq;
import com.smartcar.dororok.locationcode.repository.LocationCodeRepository;
import com.smartcar.dororok.member.domain.entitiy.Gender;
import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.member.repository.FavoriteGenresRepository;
import com.smartcar.dororok.member.repository.MemberRepository;
import com.smartcar.dororok.musicmode.domain.MusicMode;
import com.smartcar.dororok.recommendation.domain.DayPart;
import com.smartcar.dororok.recommendation.domain.dto.*;
import com.smartcar.dororok.recommendation.domain.res.PlaceDetailRes;
import com.smartcar.dororok.recommendation.domain.res.PlaceRecommendationRes;
import com.smartcar.dororok.weather.domain.GetWeatherDto;
import com.smartcar.dororok.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RecommendationService {

    private final String key = System.getenv("OPENAPI_KEY");
    private final String imageBasicUrl = System.getenv("IMAGE_BASIC_URL");
    private final String djangoBaseUrl = System.getenv("DJANGO_BASE_URL");
    private final String TourAPIURL = "http://apis.data.go.kr/B551011/KorService1";

    private final MemberRepository memberRepository;
    private final FavoriteGenresRepository favoriteGenresRepository;
    private final LocationCodeRepository locationCodeRepository;
    private final WeatherService weatherService;
    private final LocationService locationService;
    private final DestinationService destinationService;

    public List<MusicRecommendationDto> getMusicRecommendations(String lat, String lng, MusicMode musicMode, Integer isFirst) {

        GetWeatherDto weather = weatherService.getCurrentWeather(lat, lng);

        LocationInfoDto location = locationService.getAddressFromCoordinates(lat, lng);

        Member member = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);

        List<String> genres = favoriteGenresRepository.findGenreIdsByMemberId(member.getId());


//        MusicRecommendationToDjangoDto dto = MusicRecommendationToDjangoDto.builder()
//                .genres(genres)
//                .memberId(member.getId())
//                .lat(lat)
//                .lng(lng)
//                .region1depthName(location.getRegion1depthName())
//                .region2depthName(location.getRegion2depthName())
//                .region3depthName(location.getRegion3depthName())
//                .skyCondition(weather.getSkyCondition())
//                .precipitationType(weather.getPrecipitationType())
//                .musicMode(musicMode)
//                .dayPart(getCurrentDayPart())
//                .isFirst(isFirst)
//                .build();
        //이 dto 안쓰는데 일단 남겨두겠음.

        //array[string]형으로 넣기 위해
        MultiValueMap<String, String> queryParamGenre = new LinkedMultiValueMap<>();
        genres.forEach(genre -> queryParamGenre.add("genre", genre));

        System.out.println(member.getId());
        System.out.println(queryParamGenre);
        System.out.println(lat);
        System.out.println(lng);
        System.out.println(location.getRegion1depthName());
        System.out.println(location.getRegion2depthName());
        System.out.println(weather.getSkyCondition().getDescription());
        System.out.println(weather.getPrecipitationType().getDescription());
        System.out.println(musicMode.toString().toLowerCase());
        System.out.println(getCurrentDayPart().getDescription());
        System.out.println((isFirst == 1 ? "true" : "false"));
        //장고 서버에 dto 보내서 MusicRecommendationDto 결과 받기
        Map<String, Object> response = WebClient.create(djangoBaseUrl)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recommend/music/")
                        .queryParam("member_id", member.getId())
                        .queryParams(queryParamGenre)
                        .queryParam("lat", lat)
                        .queryParam("lng", lng)
                        .queryParam("region1depthName", location.getRegion1depthName())
                        .queryParam("region2depthName", location.getRegion2depthName())
                        .queryParam("region3depthName", location.getRegion3depthName())
                        .queryParam("skyCondition", weather.getSkyCondition().getDescription())
                        .queryParam("precipitationType", weather.getPrecipitationType().getDescription())
                        .queryParam("MusicMode", musicMode.toString().toLowerCase())
                        .queryParam("dayPart", getCurrentDayPart().getDescription())
                        .queryParam("is_first", (isFirst == 1 ? "true" : "false"))
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        List<MusicRecommendationDto> result = new ArrayList<>();

        //result 파싱해서 보내기!
        List<Map<String, Object>> recommendations = (List<Map<String, Object>>) response.get("recommendations");
        for (Map<String, Object> recommendation : recommendations) {
            result.add(MusicRecommendationDto.builder()
                    .title(recommendation.get("title").toString())
                    .artist(recommendation.get("artist").toString())
                    .trackID(recommendation.get("track_id").toString())
                    .albumImage(recommendation.get("album_image").toString())
                    .build());
        }
        return result;
    }

    public List<PlaceInfoDto> getPlacesRecommendation() {
        Member member = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        AgeRange ageRange = destinationService.getAgeRange(member.getBirthday());
        Gender gender = member.getGender();

        //장고 서버에 dto 보내서 지역 정보 받기
        Map<String, Object> response = WebClient.create(djangoBaseUrl)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recommend/place/")
                        .queryParam("ageRange", ageRange)
                        .queryParam("gender", gender)
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();


        List<PlaceDto> res = new ArrayList<>(); //여기로 장고에서 돌린 상위 10개 장소 받고

        List<Map<String, Object>> recommendations = (List<Map<String, Object>>) response.get("recommendations");
        for (Map<String, Object> recommendation : recommendations) {
            res.add(PlaceDto.builder()
                    .region1depthName(recommendation.get("region1depth_name").toString())
                    .region2depthName(recommendation.get("region2depth_name").toString())
                    .build());
        }

        List<PlaceInfoDto> result = new ArrayList<>();

        for(PlaceDto placeDto : res) {
            LocationCodeReq dto = LocationCodeReq.builder()
                    .region1depthName(placeDto.getRegion1depthName()) //채우기
                    .region2depthName(placeDto.getRegion2depthName()) //채우기
                    .build();
            LocationCodeDto locationCode = getLocationCode(dto);
            System.out.println(locationCode.getAreaCode());
            System.out.println(locationCode.getSigunguCode());
            PlaceInfoDto placeRecommendation = getPlaceRecommendation(locationCode);
            result.add(placeRecommendation);
        }

        return result;
    }

    //현재 위도, 경도 넣었을때 위치기반 관광정보조회 API 호출하여 DTO에 저장하는 코드
    public PlaceRecommendationRes getNearByPlacesRecommendation(String lat, String lng, Integer pageNo) {
        Map<String, Object> res = getPlacesInfoFromLocationBasedAPI(lat, lng);
        Map<String, Object> response = (Map<String, Object>) res.get("response");
        Map<String, Object> body = (Map<String, Object>) response.get("body");
        Map<String, Object> items = (Map<String, Object>) body.get("items");

        List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");

        Integer totalCounts = (Integer) body.get("totalCount");
        Integer pageNumbers = (totalCounts / 10) + 1; //로직에선 0번 페이지부터 시작하므로...

        List<PlaceInfoDto> result = getPagingPlaceList(item, pageNo - 1); //로직에선 0번 페이지부터 시작하므로..

        return PlaceRecommendationRes.builder()
                .places(result)
                .pageNumbers(pageNumbers)
                .build();
    }

    //장고 돌려서 나온 지역에서 랜덤으로 하나 뽑아서 추천
    public PlaceInfoDto getPlaceRecommendation(LocationCodeDto dto) {
        AreaBasedApiDto areaBasedApiDto = AreaBasedApiDto.builder()
                .areaCode(Integer.toString(dto.getAreaCode()))
                .sigunguCode(Integer.toString(dto.getSigunguCode()))
                .build();

        Map<String, Object> res = getPlacesInfoFromAreaBasedAPI(areaBasedApiDto);
        Map<String, Object> response = (Map<String, Object>) res.get("response");
        Map<String, Object> body = (Map<String, Object>) response.get("body");
        Map<String, Object> items = (Map<String, Object>) body.get("items");
        List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");

        List<PlaceInfoDto> result = getRandomPlaceList5(item);

        return result.get(0);

    }

    public PlaceDetailRes getPlaceDetail(String contentId) {
        Map<String, Object> res = getPlaceDetailFromAPI(contentId);
        Map<String, Object> response = (Map<String, Object>) res.get("response");
        Map<String, Object> body = (Map<String, Object>) response.get("body");
        Map<String, Object> items = (Map<String, Object>) body.get("items");
        List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");

        Map<String, Object> detail = item.get(0);

        PlaceDetailDto placeDetail = PlaceDetailDto.builder()
                .contentId((String) detail.get("contentid"))
                .title((String) detail.get("title"))
                .address((String) detail.get("addr1"))
                .imageUrl(getImageUrl((String) detail.get("firstimage")))
                .build();

        AreaBasedApiDto dto = AreaBasedApiDto.builder()
                .areaCode((String) detail.get("areacode"))
                .sigunguCode((String) detail.get("sigungucode"))
                .build();


        List<PlaceInfoDto> placeDetailList = getPlaceDetailRes(dto);

        return PlaceDetailRes.builder()
                .placeDetail(placeDetail)
                .placeList(placeDetailList)
                .build();
    }

    public PlaceDetailRes getNeraByPlaceDetail(String contentId) {
        Map<String, Object> res = getPlaceDetailFromAPI(contentId);
        Map<String, Object> response = (Map<String, Object>) res.get("response");
        Map<String, Object> body = (Map<String, Object>) response.get("body");
        Map<String, Object> items = (Map<String, Object>) body.get("items");
        List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");

        Map<String, Object> detail = item.get(0);

        PlaceDetailDto placeDetail = PlaceDetailDto.builder()
                .contentId((String) detail.get("contentid"))
                .title((String) detail.get("title"))
                .address((String) detail.get("addr1"))
                .imageUrl(getImageUrl((String) detail.get("firstimage")))
                .build();

        AreaBasedApiDto dto = AreaBasedApiDto.builder()
                .areaCode((String) detail.get("areacode"))
                .sigunguCode((String) detail.get("sigungucode"))
                .cat1((String) detail.get("cat1"))
                .cat2((String) detail.get("cat2"))
                //.cat3((String)detail.get("cat3"))
                .build();

        List<PlaceInfoDto> placeDetailList = getPlaceDetailRes(dto);

        return PlaceDetailRes.builder()
                .placeDetail(placeDetail)
                .placeList(placeDetailList)
                .build();


    }

    //지역기반 관광정보조회	API 호출하는 코드 (JSON을 Map에 저장)
    public Map<String, Object> getPlacesInfoFromAreaBasedAPI(AreaBasedApiDto dto) {
        //대분류, 중분류, 소분류가 없을 경우
        if (dto.getCat1() == null || dto.getCat2() == null || dto.getCat3() == null) {
            return WebClient.create(TourAPIURL)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/areaBasedList1")
                            .queryParam("numOfRows", 300)
                            .queryParam("serviceKey", key)
                            .queryParam("MobileOS", "AND")
                            .queryParam("MobileApp", "dororok")
                            .queryParam("_type", "JSON")
                            .queryParam("contentTypeId", 12)
                            .queryParam("arrange", "O")
                            .queryParam("areaCode", dto.getAreaCode())
                            .queryParam("sigunguCode", dto.getSigunguCode())
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();
        }
        return WebClient.create(TourAPIURL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/areaBasedList1")
                        .queryParam("numOfRows", 300)
                        .queryParam("serviceKey", key)
                        .queryParam("MobileOS", "AND")
                        .queryParam("MobileApp", "dororok")
                        .queryParam("_type", "JSON")
                        .queryParam("contentTypeId", 12)
                        .queryParam("arrange", "O")
                        .queryParam("areaCode", dto.getAreaCode())
                        .queryParam("sigunguCode", dto.getSigunguCode())
                        .queryParam("cat1", dto.getCat1())
                        .queryParam("cat2", dto.getCat2())
                        .queryParam("cat3", dto.getCat3())
                        .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }


    //위치기반 관광정보조회	API 호출하는 코드 (JSON을 Map에 저장)
    public Map<String, Object> getPlacesInfoFromLocationBasedAPI(String lat, String lng) {

        return WebClient.create(TourAPIURL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/locationBasedList1")
                        .queryParam("numOfRows", 300)
                        .queryParam("serviceKey", key)
                        .queryParam("MobileOS", "AND")
                        .queryParam("MobileApp", "dororok")
                        .queryParam("_type", "JSON")
                        .queryParam("arrange", "O")
                        .queryParam("contentTypeId", 12)
                        .queryParam("mapX", lng)
                        .queryParam("mapY", lat)
                        .queryParam("radius", 7000)
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
                        .queryParam("areacodeYN", "Y")
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

    //areacode, sigungucode 얻어오는 코드
    private LocationCodeDto getLocationCode(LocationCodeReq req) {
        String areaName = req.getRegion1depthName();
        StringTokenizer st = new StringTokenizer(req.getRegion2depthName(), " ");
        String sigunguName = st.nextToken();

        LocationCode locationCode = locationCodeRepository.findByAreaNameAndSigunguName(areaName, sigunguName).orElse(null);

        return LocationCodeDto.builder()
                .areaCode(locationCode.getAreaCode())
                .sigunguCode(locationCode.getSigunguCode())
                .build();

    }

    //장소 리스트에서 랜덤으로 10개의 장소 뽑는 코드
    private List<PlaceInfoDto> getRandomPlaceList5(List<Map<String, Object>> item) {
        // 섞기
        Collections.shuffle(item);

        // 5개 항목 선택
        List<Map<String, Object>> selectedItems = item.size() > 5 ? item.subList(0, 5) : item;
        System.out.println(selectedItems);
        List<PlaceInfoDto> result = new ArrayList<>();
        for (Map<String, Object> place : selectedItems) {
            LocationInfoDto location = locationService.getAddressFromCoordinates((String) place.get("mapy"), (String) place.get("mapx"));
            PlaceInfoDto placeRecommendationDto = PlaceInfoDto.builder()
                    .region1depthName(location.getRegion1depthName())
                    .region2depthName(location.getRegion2depthName())
                    .region3depthName(location.getRegion3depthName())
                    .address((String) place.get("addr1"))
                    .contentId((String) place.get("contentid"))
                    .imageUrl(getImageUrl((String) place.get("firstimage")))
                    .title((String) place.get("title"))
                    .build();
            result.add(placeRecommendationDto);
        }
        return result;
    }

    //장소 리스트에서 랜덤으로 10개의 장소 뽑는 코드
    private List<PlaceInfoDto> getRandomPlaceList(List<Map<String, Object>> item) {
        // 섞기
        Collections.shuffle(item);

        // 10개 항목 선택
        List<Map<String, Object>> selectedItems = item.size() > 10 ? item.subList(0, 10) : item;

        List<PlaceInfoDto> result = new ArrayList<>();
        for (Map<String, Object> place : selectedItems) {
            LocationInfoDto location = locationService.getAddressFromCoordinates((String) place.get("mapy"), (String) place.get("mapx"));
            PlaceInfoDto placeRecommendationDto = PlaceInfoDto.builder()
                    .region1depthName(location.getRegion1depthName())
                    .region2depthName(location.getRegion2depthName())
                    .region3depthName(location.getRegion3depthName())
                    .address((String) place.get("addr1"))
                    .contentId((String) place.get("contentid"))
                    .imageUrl(getImageUrl((String) place.get("firstimage")))
                    .title((String) place.get("title"))
                    .build();
            result.add(placeRecommendationDto);
        }
        return result;
    }

    //장소 리스트에서 10개씩 페이징 하는거 구현
    private List<PlaceInfoDto> getPagingPlaceList(List<Map<String, Object>> item, int pageNo) {
        // 랜덤 시드 설정
        Collections.shuffle(item, new Random(12345L));

        // 페이지 당 항목 수
        int pageSize = 10;

        // 시작 인덱스 계산
        int fromIndex = pageNo * pageSize;

        // 종료 인덱스 계산
        int toIndex = Math.min(fromIndex + pageSize, item.size());

        // 페이지 범위가 리스트 크기를 초과할 경우 빈 리스트 반환
        if (fromIndex >= item.size()) {
            return new ArrayList<>();
        }

        // 페이징된 항목 선택
        List<Map<String, Object>> selectedItems = item.subList(fromIndex, toIndex);

        List<PlaceInfoDto> result = new ArrayList<>();
        for (Map<String, Object> place : selectedItems) {
            LocationInfoDto location = locationService.getAddressFromCoordinates((String) place.get("mapy"), (String) place.get("mapx"));
            PlaceInfoDto placeRecommendationDto = PlaceInfoDto.builder()
                    .region1depthName(location.getRegion1depthName())
                    .region2depthName(location.getRegion2depthName())
                    .region3depthName(location.getRegion3depthName())
                    .address((String) place.get("addr1"))
                    .contentId((String) place.get("contentid"))
                    .imageUrl(getImageUrl((String) place.get("firstimage")))
                    .title((String) place.get("title"))
                    .build();
            result.add(placeRecommendationDto);
        }
        return result;
    }

    //이미지 없으면 default.png 리턴
    private String getImageUrl(String imageUrl) {
        if (imageUrl.isEmpty()) {
            return imageBasicUrl + "/images/default.png";
        }
        return imageUrl;
    }

    private List<PlaceInfoDto> getPlaceDetailRes(AreaBasedApiDto dto) {
        Map<String, Object> res = getPlacesInfoFromAreaBasedAPI(dto);

        Map<String, Object> response = (Map<String, Object>) res.get("response");
        Map<String, Object> body = (Map<String, Object>) response.get("body");
        Map<String, Object> items = (Map<String, Object>) body.get("items");
        List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");

        List<PlaceInfoDto> result = getRandomPlaceList(item);

        return result;
    }

    private DayPart getCurrentDayPart() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();

        if (hour >= 6 && hour < 12) {
            return DayPart.MORNING;
        } else if (hour >= 12 && hour < 18) {
            return DayPart.AFTERNOON;
        } else if (hour >= 18 && hour < 24) {
            return DayPart.EVENING;
        } else {
            return DayPart.DAYBREAK;
        }
    }

}
