package com.smartcar.dororok.music.repository;

import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.music.domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface MusicRepository extends JpaRepository<Music, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Music m WHERE m.createdAt < :threshold")
    void deleteOlderThan(LocalDateTime threshold);

    void deleteByMember(Member member);
}
