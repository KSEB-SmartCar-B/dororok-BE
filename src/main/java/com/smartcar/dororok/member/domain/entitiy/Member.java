package com.smartcar.dororok.member.domain.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

    @Id // 카카오에서 제공하는 카카오ID
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDateTime birthday;

    @Column(nullable = false)
    private Boolean privacyAgreement;

    @Column(nullable = false)
    private Boolean locationInfoAgreement;

    @Column(nullable = false)
    private String username; //프런트에서 주는 액세스 토큰으로 받아오는 카카오 계정의 이메일 (중복방지 위해!)

    @ColumnDefault("'n'")
    private String password;

    private String refreshToken;

//    @ManyToMany// 나중에 관계 entity 만들어서 @ManyToOne 두개로 수정
//    @Column(name = "favoirte_genres")
//    private Set<Genre> favoriteGenres;


    public Member(Long id, Gender gender, String nickname, LocalDateTime birthday, Boolean privacyAgreement, Boolean locationInfoAgreement, String username, String password, String refreshToken, List<String> roles) {
        this.id = id;
        this.gender = gender;
        this.nickname = nickname;
        this.birthday = birthday;
        this.privacyAgreement = privacyAgreement;
        this.locationInfoAgreement = locationInfoAgreement;
        this.username = username;
        this.password = password;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

