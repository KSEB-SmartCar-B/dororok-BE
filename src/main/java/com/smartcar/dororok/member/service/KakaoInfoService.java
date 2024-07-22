package com.smartcar.dororok.member.service;

import com.smartcar.dororok.member.domain.dto.KakaoInfoDto;
import com.smartcar.dororok.member.domain.dto.MemberDto;
import com.smartcar.dororok.member.domain.entitiy.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class KakaoInfoService { // 카카오 API를 이용해 토큰을 전송하여 유저 정보를 요청

    public Map<String, Object> getUserAttributesByToken(String accessToken){
        return WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    // 카카오API에서 가져온 유저정보를 MemberDto에 저장
    public MemberDto getUserProfileByToken(String accessToken){
        Map<String, Object> userAttributesByToken = getUserAttributesByToken(accessToken);
        KakaoInfoDto kakaoInfoDto = new KakaoInfoDto(userAttributesByToken);
        MemberDto memberDto = MemberDto.builder()
                .id(kakaoInfoDto.getId())
                .profileImageUrl(kakaoInfoDto.getProfileImageUrl())
                .build();

        return memberDto;
    }


}
