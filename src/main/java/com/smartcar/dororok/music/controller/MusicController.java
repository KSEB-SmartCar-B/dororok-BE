package com.smartcar.dororok.music.controller;

import com.smartcar.dororok.music.domain.dto.MusicDto;
import com.smartcar.dororok.music.domain.res.PostMusicRes;
import com.smartcar.dororok.music.service.MusicService;
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
@RequestMapping("/music")
@Tag(name = "Music Controller", description = "음악 관련 API")
public class MusicController {

    private final MusicService musicService;

    @PostMapping
    @Operation(summary = "사용자가 검색하여 일정 시간 이상 들은 음악을 음악 추천을 위한 DB에 저장", description = "사용자가 일정 시간 이상 들은 음악을 음악 추천을 위한 DB에 저장, trackId만 보내주면 됌!")
    public ResponseEntity<PostMusicRes> saveMusic(@RequestBody MusicDto musicDto) {

        musicService.saveMusic(musicDto);

        return ResponseEntity.ok(PostMusicRes.builder()
                .response("success")
                .build());
    }
}
