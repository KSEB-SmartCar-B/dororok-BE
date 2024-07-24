package com.smartcar.dororok.recommendation.service;

import com.smartcar.dororok.global.auth.utils.SecurityUtils;
import com.smartcar.dororok.location.dto.LocationDto;
import com.smartcar.dororok.location.service.LocationService;
import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.member.repository.FavoriteGenresRepository;
import com.smartcar.dororok.member.repository.MemberRepository;
import com.smartcar.dororok.recommendation.domain.MusicMode;
import com.smartcar.dororok.recommendation.domain.dto.MusicRecommendationDto;
import com.smartcar.dororok.recommendation.domain.dto.MusicRecommendationToDjangoDto;
import com.smartcar.dororok.weather.domain.GetWeatherDto;
import com.smartcar.dororok.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RecommendationService {

    private final MemberRepository memberRepository;
    private final FavoriteGenresRepository favoriteGenresRepository;
    private final WeatherService weatherService;
    private final LocationService locationService;

    public List<MusicRecommendationDto> getMusicRecommendations(String lat, String lng, MusicMode musicMode) {

        GetWeatherDto weather = weatherService.getCurrentWeather(lat,lng);

        LocationDto location = locationService.getAddressFromCoordinates(lat, lng);

        Member member = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);

        List<String> genres = favoriteGenresRepository.findGenreNamesByMemberId(member.getId());

        MusicRecommendationToDjangoDto dto = MusicRecommendationToDjangoDto.builder()
                .genres(genres)
                .lat(lat)
                .lng(lng)
                .province(location.getProvinceName())
                .city(location.getCityName())
                .skyCondition(weather.getSkyCondition())
                .precipitationType(weather.getPrecipitationType())
                .musicMode(musicMode)
                .build();

        log.info("getMusicRecommendations: {}", dto);
        //장고 서버에 dto 보내서 MusicRecommendationDto 결과 받기
        return null;
    }
}
