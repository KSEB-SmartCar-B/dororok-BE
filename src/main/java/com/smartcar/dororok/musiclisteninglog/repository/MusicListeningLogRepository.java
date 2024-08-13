package com.smartcar.dororok.musiclisteninglog.repository;

import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.musiclisteninglog.domain.MusicListeningLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MusicListeningLogRepository extends JpaRepository<MusicListeningLog, Long> {

    @Modifying
    @Query("DELETE FROM MusicListeningLog m WHERE m.createdAt < :cutoffDate")
    void deleteOlderThan(LocalDateTime cutoffDate);

    void deleteByMember(Member member);
}
