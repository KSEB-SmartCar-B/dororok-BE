package com.smartcar.dororok.member.service;

import com.smartcar.dororok.favorites.service.FavoritesService;
import com.smartcar.dororok.global.auth.dto.JwtToken;
import com.smartcar.dororok.global.auth.service.JwtTokenService;
import com.smartcar.dororok.global.auth.utils.SecurityUtils;
import com.smartcar.dororok.global.exception.CustomException;
import com.smartcar.dororok.global.exception.ErrorCode;
import com.smartcar.dororok.member.domain.dto.*;
import com.smartcar.dororok.member.domain.entitiy.FavoriteGenres;
import com.smartcar.dororok.genre.domain.Genre;
import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.member.repository.FavoriteGenresRepository;
import com.smartcar.dororok.genre.repository.GenreRepository;
import com.smartcar.dororok.member.repository.MemberRepository;
import com.smartcar.dororok.musiclisteninglog.repository.MusicListeningLogRepository;
import com.smartcar.dororok.search.service.SearchService;
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
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final GenreRepository genreRepository;
    private final FavoriteGenresRepository favoriteGenresRepository;
    private final MusicListeningLogRepository musicListeningLogRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenService jwtTokenService;
    private final KakaoInfoService kakaoInfoService;
    private final SearchService searchService;
    private final FavoritesService favoritesService;

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
                .profileImageUrl(getProfileImageUrl(signUpDto.getKakaoAccessToken()))
                .roles(roles)
                .build();

        memberRepository.save(member);
    }

    public void addFavoriteGenres(SignUpDto signUpDto) {
        List<FavoriteGenreDto> favoriteGenreNames = signUpDto.getFavoriteGenreLists();
        Member member = memberRepository.findByUsername(getUsername(signUpDto.getKakaoAccessToken())).orElse(null);
        for (FavoriteGenreDto favoriteGenreName : favoriteGenreNames) {
            String genreName = favoriteGenreName.getName();
            Genre genre = genreRepository.findByName(genreName);
            if (genre == null) {
                throw new CustomException(ErrorCode.BAD_REQUEST);
            }
            FavoriteGenres favoriteGenres = new FavoriteGenres(member, genre);
            favoriteGenresRepository.save(favoriteGenres);
        }
    }

    public Boolean isSignedUp(String kakaoAccessToken) {
        Member member = memberRepository.findByUsername(getUsername(kakaoAccessToken)).orElse(null);
        return member != null;
    }



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

    //카카오 고유 아이디로 로그인
    public JwtToken signInWithUsername(String username) {
        // 1. 카카오 계정 이메일을 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, "n");

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenService.generateToken(authentication);

        //db에 refreshToken 저장
        memberRepository.updateRefreshToken(username, jwtToken.getRefreshToken());

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

    public InfoDto getInfo() {
        String currentUsername = SecurityUtils.getCurrentUsername();
        Member member = memberRepository.findByUsername(currentUsername).orElse(null);

        return InfoDto.builder()
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .gender(member.getGender())
                .profileImgUrl(member.getProfileImageUrl())
                .build();
    }

    public List<FavoriteGenreDto> getFavoriteGenreNames() {
        String currentUsername = SecurityUtils.getCurrentUsername();
        Member member = memberRepository.findByUsername(currentUsername).orElse(null);

        List<FavoriteGenres> names = member.getFavoriteGenres();

        List<FavoriteGenreDto> result = new ArrayList<>();

        for (FavoriteGenres favoriteGenre : names) {
            result.add(FavoriteGenreDto.builder()
                            .name(favoriteGenre.getGenre().getName())
                            .build()
                    );
        }

        return result;
    }

    public boolean patchInfo(PatchInfoDto infoDto) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        Member member = memberRepository.findByUsername(currentUsername).orElse(null);

        boolean isUpdated = false;

        if (infoDto.getGender() != null && !infoDto.getGender().equals(member.getGender())) {
            member.setGender(infoDto.getGender());
            isUpdated = true;
        }
        if (infoDto.getNickname() != null && !infoDto.getNickname().equals(member.getNickname())) {
            member.setNickname(infoDto.getNickname());
            isUpdated = true;
        }
        if (infoDto.getBirthday() != null && !infoDto.getBirthday().equals(member.getBirthday())) {
            member.setBirthday(infoDto.getBirthday());
            isUpdated = true;
        }

        if (isUpdated) {
            memberRepository.flush();
        }

        return isUpdated;
    }


    public boolean patchFavoriteGenres(List<FavoriteGenreDto> dto) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        Member member = memberRepository.findByUsername(currentUsername).orElse(null);

        favoriteGenresRepository.deleteByMember(member);

        boolean isUpdated = false;

        for (FavoriteGenreDto favoriteGenreName : dto) {
            String genreName = favoriteGenreName.getName();
            Genre genre = genreRepository.findByName(genreName);
            if (genre == null) {
                isUpdated = false;
            }
            FavoriteGenres favoriteGenres = new FavoriteGenres(member, genre);
            favoriteGenresRepository.save(favoriteGenres);
            isUpdated = true;
        }
        return isUpdated;
    }

//    public JwtToken signInTest(String email) {
//        // 1. 카카오 계정 이메일을 기반으로 Authentication 객체 생성
//        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, "n");
//
//        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
//        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        JwtToken jwtToken = jwtTokenService.generateToken(authentication);
//
//        memberRepository.updateRefreshToken(email, jwtToken.getRefreshToken());
//
//
//        return jwtToken;
//    }

    public void deleteAccount() {
        Member findMember = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);

        //favoriteGenres 삭제
        favoriteGenresRepository.deleteByMember(findMember);
        //member_roles 삭제
        findMember.getRoles().clear();
        //music 삭제
        musicListeningLogRepository.deleteByMember(findMember);
        //최근 검색기록 삭제
        searchService.deleteAllSearchLogs();
        //
        favoritesService.deleteFavoritesByMember(findMember);
        //member 삭제
        memberRepository.delete(findMember);
    }

    public String getUsername(String token) {
        return String.valueOf(kakaoInfoService.getUserProfileByToken(token).getId());
    }

    public String getProfileImageUrl(String token) {
        return kakaoInfoService.getUserProfileByToken(token).getProfileImageUrl();
    }




}

