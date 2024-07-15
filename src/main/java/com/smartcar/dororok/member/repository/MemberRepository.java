package com.smartcar.dororok.member.repository;

import com.smartcar.dororok.member.domain.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    @Modifying
    @Query("UPDATE Member m SET m.refreshToken = :refreshToken WHERE m.username = :username")
    void updateRefreshToken(@Param("username") String username, @Param("refreshToken") String refreshToken);


    @Query("SELECT m FROM Member m WHERE m.refreshToken = :refreshToken")
    Optional<Member> findMemberByRefreshToken(@Param("refreshToken") String refreshToken);
}
