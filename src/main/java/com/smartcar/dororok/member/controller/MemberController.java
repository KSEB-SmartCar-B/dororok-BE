package com.smartcar.dororok.member.controller;

import com.smartcar.dororok.global.auth.dto.JwtToken;
import com.smartcar.dororok.member.domain.dto.*;
import com.smartcar.dororok.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "Member Controller", description = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/isSigned")
    @Operation(summary = "가입 여부", description = "카카오 액세스 토큰 이용하여 가입여부 판단 할 때 사용하는 API")
    public ResponseEntity<IsSignedDto> isSigned(@RequestBody KakaoAccessTokenDto kakaoAccessTokenDto) {

        String kakaoAccessToken = kakaoAccessTokenDto.getKakaoAccessToken();

        IsSignedDto isSignedDto = new IsSignedDto();

        isSignedDto.setIsSigned(memberService.isSignedUp(kakaoAccessToken));

        return ResponseEntity.ok(isSignedDto);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "카카오 액세스 토큰 이용하여 로그인 할 때 사용하는 API")
    public ResponseEntity<JwtToken> signIn(@RequestBody KakaoAccessTokenDto kakaoAccessTokenDto) {

        String kakaoAccessToken = kakaoAccessTokenDto.getKakaoAccessToken();

        return ResponseEntity.ok(memberService.signIn(kakaoAccessToken));
    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "카카오 액세스 토큰 이용하여 회원가입 할 때 사용하는 API, 장르 보낼 때 \"favoriteGenreLists\": [\"장르이름\", \"장르이름\", \"장르이름\"] 헝식으로 보내면 됨!")
    public ResponseEntity<JwtToken> signUp(@RequestBody SignUpDto signUpDto) {

        memberService.signUp(signUpDto);
        memberService.addFavoriteGenres(signUpDto);

        return ResponseEntity.ok(memberService.signIn(signUpDto.getKakaoAccessToken()));
    }

    @PostMapping("/token/refresh")
    @Operation(summary = "액세스 토큰 재발급", description = "액세스 토큰이 만료됐을 때, 리프레시 토큰으로 액세스 토큰 재발급")
    public ResponseEntity<AccessTokenDto> tokenRefresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseEntity.ok(memberService.refreshAccessToken(refreshTokenDto));
    }

    @GetMapping("/current-info")
    @Operation(summary = "현재 유저의개인 정보 조회", description = "현재 로그인 한 유져의 개인 정보(닉네임, 생년월일, 성별) 조회하는 API")
    public ResponseEntity<CurrentInfoDto> currentInfo() {
        return ResponseEntity.ok(memberService.getCurrentInfo());
    }

    @GetMapping("/current-favorite-genre")
    @Operation(summary = "현재 유저의 선호 장르 조회", description = "현재 로그인 한 유저의 선호 장르 조회하는 API")
    public ResponseEntity<List<CurrentGenreDto>> getCurrentGenresNames() {
        return ResponseEntity.ok(memberService.getCurrentGenreNames());
    }

    @GetMapping("/sign-in/test")
    public JwtToken signInTest(@RequestParam String email) {
        return memberService.signInTest(email);
    }

    @GetMapping("/test")
    @Operation(summary = "로그인 테스트", description = "로그인 하지않고 호출시, 오류발생")
    public String test() {
        return "success";
    }
}



