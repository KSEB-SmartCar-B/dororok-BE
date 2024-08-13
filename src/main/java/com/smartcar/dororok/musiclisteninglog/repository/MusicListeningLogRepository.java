package com.smartcar.dororok.musiclisteninglog.repository;

import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.musiclisteninglog.domain.MusicListeningLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicListeningLogRepository extends JpaRepository<MusicListeningLog, Long> {

    void deleteByMember(Member member);
}
