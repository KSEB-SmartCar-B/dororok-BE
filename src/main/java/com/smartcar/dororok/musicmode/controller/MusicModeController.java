package com.smartcar.dororok.musicmode.controller;

import com.smartcar.dororok.musicmode.domain.MusicModeRes;
import com.smartcar.dororok.musicmode.service.MusicModeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/musicmode")
@Tag(name = "MusicMode Controller", description = "뮤직모드 관련 API")
public class MusicModeController {

    private final MusicModeService musicModeService;

    @GetMapping
    @Operation(summary = "모든 뮤직모드 이름, 이미지경로 리스트", description = "모든 뮤직모드 이름, 이미지경로 리스트")
    public ResponseEntity<MusicModeRes> getMusicMode() {
        return ResponseEntity.ok(musicModeService.getMusicMode());
    }
}
