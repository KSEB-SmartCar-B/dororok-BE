package com.smartcar.dororok.musiclisteninglog.controller;

import com.smartcar.dororok.musiclisteninglog.domain.dto.MusicDto;
import com.smartcar.dororok.musiclisteninglog.domain.res.PostMusicRes;
import com.smartcar.dororok.musiclisteninglog.service.MusicListeningLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music-log")
@Tag(name = "MusicListeningLog Controller", description = "음악 기록 관련 API")
public class MusicListeningLogController {

    private final MusicListeningLogService musicListeningLogService;

    @PostMapping
    @Operation(summary = "사용자가 검색하여 일정 시간 이상 들은 음악을 음악 추천을 위한 DB에 저장", description = "사용자가 일정 시간 이상 들은 음악을 음악 추천을 위한 DB에 저장, trackId만 보내주면 됌!")
    public ResponseEntity<PostMusicRes> saveMusic(@RequestBody MusicDto musicDto) {

        musicListeningLogService.saveMusic(musicDto);

        return ResponseEntity.ok(PostMusicRes.builder()
                .response("success")
                .build());
    }
}
