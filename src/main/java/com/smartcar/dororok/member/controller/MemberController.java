package com.smartcar.dororok.member.controller;

import com.smartcar.dororok.global.auth.dto.JwtToken;
import com.smartcar.dororok.global.exception.CustomException;
import com.smartcar.dororok.global.exception.ErrorCode;
import com.smartcar.dororok.member.domain.dto.AccessTokenDto;
import com.smartcar.dororok.member.domain.dto.KakaoAccessTokenDto;
import com.smartcar.dororok.member.domain.dto.RefreshTokenDto;
import com.smartcar.dororok.member.domain.dto.SignUpDto;
import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "Member Controller", description = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "카카오 액세스 토큰 이용하여 로그인 할 때 사용하는 API")
    public ResponseEntity<JwtToken> signIn(@RequestBody KakaoAccessTokenDto kakaoAccessTokenDto) {

        String kakaoAccessToken = kakaoAccessTokenDto.getKakaoAccessToken();

        Boolean signedUp = memberService.isSignedUp(kakaoAccessToken);
        if (!signedUp) {
            throw new CustomException(ErrorCode.NOT_EXIST_USER);
        }
        return ResponseEntity.ok(memberService.signIn(kakaoAccessToken));

    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "카카오 액세스 토큰 이용하여 회원가입 할 때 사용하는 API")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {

        Boolean signedUp = memberService.isSignedUp(signUpDto.getKakaoAccessToken());
        if (signedUp) {
            throw new CustomException(ErrorCode.EXIST_USER);
        }
        memberService.signUp(signUpDto);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/token/refresh")
    @Operation(summary = "액세스 토큰 재발급", description = "액세스 토큰이 만료됐을 때, 리프레시 토큰으로 액세스 토큰 재발급")
    public ResponseEntity<AccessTokenDto> tokenRefresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseEntity.ok(memberService.refreshAccessToken(refreshTokenDto));
    }

    @GetMapping("/sign-in/test")
    public JwtToken signInTest(@RequestParam String email) {
        return memberService.signInTest(email);
    }

//    @GetMapping("/test")
//    public String test() {
//        return "success";
}



