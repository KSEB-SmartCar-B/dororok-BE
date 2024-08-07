package com.smartcar.dororok.locationcode.service;

import com.smartcar.dororok.locationcode.domain.LocationCode;
import com.smartcar.dororok.locationcode.domain.dto.LocationCodeDto;
import com.smartcar.dororok.locationcode.domain.req.LocationCodeReq;
import com.smartcar.dororok.locationcode.repository.LocationCodeRepository;
import com.smartcar.dororok.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationCodeService {

    private final RecommendationService recommendationService;
    private final LocationCodeRepository locationCodeRepository;


    //지역코드, 시군구코드 추가
    public void save() {

        List<String> areaName = new ArrayList<>(Arrays.asList("서울", "인천", "대전", "대구", "광주", "부산", "울산", "세종특별자치시", "경기", "강원특별자치도", "충북", "충남", "경북", "경남", "전북특별자치도", "전남", "제주특별자치도"));
        List<Integer> areaCode = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 31, 32, 33, 34, 35, 36, 37, 38, 39));

        for (int i = 0; i < areaCode.size(); i++) {
            Map<String, Object> placeAreaCodeFromAPI = recommendationService.getPlaceAreaCodeFromAPI(areaCode.get(i));
            Map<String, Object> response = (Map<String, Object>) placeAreaCodeFromAPI.get("response");
            Map<String, Object> body = (Map<String, Object>) response.get("body");
            Map<String, Object> items = (Map<String, Object>) body.get("items");
            List<Map<String, Object>> item = (List<Map<String, Object>>) items.get("item");

            for (Map<String, Object> sigungu : item) {
                String code = (String) sigungu.get("code");
                String name = (String) sigungu.get("name");

                locationCodeRepository.save(LocationCode.builder()
                        .areaCode(areaCode.get(i))
                        .areaName(areaName.get(i))
                        .sigunguCode(Integer.parseInt(code))
                        .sigunguName(name)
                        .build());
            }
        }
    }

    public LocationCodeDto getLocationCode (LocationCodeReq req) {
        String areaName = req.getRegion1depthName();
        StringTokenizer st = new StringTokenizer(req.getRegion2depthName(), " ");
        String sigunguName = st.nextToken();

        LocationCode locationCode = locationCodeRepository.findByAreaNameAndSigunguName(areaName, sigunguName).orElse(null);

        return LocationCodeDto.builder()
                .areaCode(locationCode.getAreaCode())
                .sigunguCode(locationCode.getSigunguCode())
                .build();

    }
}
