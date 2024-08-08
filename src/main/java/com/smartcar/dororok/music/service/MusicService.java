package com.smartcar.dororok.music.service;

import com.smartcar.dororok.global.auth.utils.SecurityUtils;
import com.smartcar.dororok.member.domain.entitiy.Member;
import com.smartcar.dororok.member.repository.MemberRepository;
import com.smartcar.dororok.music.domain.Music;
import com.smartcar.dororok.music.domain.dto.MusicDto;
import com.smartcar.dororok.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicService {

    private final MusicRepository musicRepository;
    private final MemberRepository memberRepository;

    public void saveMusic(MusicDto musicDto) {
        Member findMember = memberRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        String trackId = musicDto.getTrackId();

        musicRepository.save(Music.builder()
                .trackId(trackId)
                .member(findMember)
                .createdAt(LocalDateTime.now())
                .build());
    }

    // 매 시간마다 추가 된지 1달 지난 노래 삭제하는 코드
    @Scheduled(cron = "0 * * * * ?") // 매 시간 정각에 실행
    public void deleteOldMusic() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        musicRepository.deleteOlderThan(oneMonthAgo);

    }

}
