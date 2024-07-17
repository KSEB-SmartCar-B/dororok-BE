package com.smartcar.dororok.member.service;

import com.smartcar.dororok.global.auth.dto.JwtToken;
import com.smartcar.dororok.global.auth.service.JwtTokenService;
import com.smartcar.dororok.global.exception.CustomException;
import com.smartcar.dororok.global.exception.ErrorCode;
import com.smartcar.dororok.member.domain.dto.AccessTokenDto;
import com.smartcar.dororok.member.domain.dto.FavoriteGenreDto;
import com.smartcar.dororok.member.domain.dto.RefreshTokenDto;
import com.smartcar.dororok.member.domain.dto.SignUpDto;
import com.smartcar.dororok.member.domain.entitiy.FavoriteGenres;
import com.smartcar.dororok.member.domain.entitiy.Genre;
import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.member.repository.FavoriteGenresRepository;
import com.smartcar.dororok.member.repository.GenreRepository;
import com.smartcar.dororok.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final GenreRepository genreRepository;
    private final FavoriteGenresRepository favoriteGenresRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenService jwtTokenService;
    private final KakaoInfoService kakaoInfoService;

    @Transactional
    public void signUp(SignUpDto signUpDto) {
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        Member member = Member.builder()
                .username(getUsername(signUpDto.getKakaoAccessToken()))
                .nickname(signUpDto.getNickname())
                .birthday(signUpDto.getBirthday())
                .gender(signUpDto.getGender())
                .privacyAgreement(signUpDto.getPrivacyAgreement())
                .locationInfoAgreement(signUpDto.getLocationInfoAgreement())
                .roles(roles)
                .build();

        memberRepository.save(member);
    }

    @Transactional
    public void addFavoriteGenres(SignUpDto signUpDto) {
        List<FavoriteGenreDto> favoriteGenreNames = signUpDto.getFavoriteGenreLists();
        for (FavoriteGenreDto favoriteGenreName : favoriteGenreNames) {
            String genreName = favoriteGenreName.getName();
            Genre genre = genreRepository.findByName(genreName);
            if (genre == null) {
                throw new CustomException(ErrorCode.BAD_REQUEST);
            }

            Member member = memberRepository.findByUsername(getUsername(signUpDto.getKakaoAccessToken())).orElse(null);
            FavoriteGenres favoriteGenres = new FavoriteGenres(member, genre);
            favoriteGenresRepository.save(favoriteGenres);
        }
    }

    @Transactional
    public Boolean isSignedUp(String kakaoAccessToken) {
        Member member = memberRepository.findByUsername(getUsername(kakaoAccessToken)).orElse(null);
        return member != null;
    }


    @Transactional
    public JwtToken signIn(String kakaoAccessToken) {
        // 1. 카카오 계정 이메일을 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(getUsername(kakaoAccessToken), "n");

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenService.generateToken(authentication);

        //db에 refreshToken 저장
        memberRepository.updateRefreshToken(getUsername(kakaoAccessToken), jwtToken.getRefreshToken());

        return jwtToken;
    }

    public AccessTokenDto refreshAccessToken(RefreshTokenDto refreshTokenDto) {
        Member member = memberRepository.findMemberByRefreshToken(refreshTokenDto.getRefreshToken()).orElse(null);
        if (member == null) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getUsername(), "n");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenService.refreshAccessToken(authentication);

    }

    @Transactional
    public JwtToken signInTest(String email) {
        // 1. 카카오 계정 이메일을 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, "n");

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenService.generateToken(authentication);

        memberRepository.updateRefreshToken(email, jwtToken.getRefreshToken());


        return jwtToken;
    }

    public Long getKakaoId(String token) {
        return kakaoInfoService.getUserProfileByToken(token).getId();
    }

    public String getUsername(String token) {
        return String.valueOf(kakaoInfoService.getUserProfileByToken(token).getId());
    }
}

