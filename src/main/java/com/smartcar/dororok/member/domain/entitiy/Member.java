package com.smartcar.dororok.member.domain.entitiy;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate //변경된 필드만 업데이트 쿼리 날려줌
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private Boolean privacyAgreement;

    @Column(nullable = false)
    private Boolean locationInfoAgreement;

    @Column(nullable = false)
    private String username; //프런트에서 주는 액세스 토큰으로 받아오는 카카오 계정의 회원번호 (중복방지 위해!)

    @ColumnDefault("'n'")
    private String password;

    private String refreshToken;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteGenres> favoriteGenres;

//    public Member(Long id, Gender gender, String nickname, LocalDate birthday, Boolean privacyAgreement, Boolean locationInfoAgreement, String username, String password, String refreshToken, String profileImageUrl, List<String> roles) {
//        this.id = id;
//        this.gender = gender;
//        this.nickname = nickname;
//        this.birthday = birthday;
//        this.privacyAgreement = privacyAgreement;
//        this.locationInfoAgreement = locationInfoAgreement;
//        this.username = username;
//        this.password = password;
//        this.refreshToken = refreshToken;
//        this.profileImageUrl = profileImageUrl;
//        this.roles = roles;
//    }

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

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}

