package com.smartcar.dororok.favorites.controller;

import com.smartcar.dororok.favorites.domain.dto.FavoritesMusicDto;
import com.smartcar.dororok.favorites.domain.dto.FavoritesPlaceDto;
import com.smartcar.dororok.favorites.domain.req.DeleteMusicListReq;
import com.smartcar.dororok.favorites.domain.req.DeleteMusicReq;
import com.smartcar.dororok.favorites.domain.req.DeletePlaceListReq;
import com.smartcar.dororok.favorites.domain.req.DeletePlaceReq;
import com.smartcar.dororok.favorites.domain.res.ExistRes;
import com.smartcar.dororok.favorites.domain.res.FavoritesMusicRes;
import com.smartcar.dororok.favorites.domain.res.FavoritesPlaceRes;
import com.smartcar.dororok.favorites.domain.res.FavoritesApiRes;
import com.smartcar.dororok.favorites.service.FavoritesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
@Tag(name = "Favorites Controller", description = "좋아요 관련 API")
public class FavoritesController {

    private final FavoritesService favoritesService;

    @PostMapping("/music")
    @Operation(summary = "유저가 좋아하는 음악 추가", description = "현재 로그인 한 유저가 좋아하는 음악 추가하는 API")
    public ResponseEntity<FavoritesApiRes> saveMusic(@RequestBody FavoritesMusicDto dto){
        return ResponseEntity.ok(favoritesService.saveMusic(dto));
    }

    @PostMapping("/place")
    @Operation(summary = "유저가 좋아하는 장소 추가", description = "현재 로그인 한 유저가 좋아하는 장소 추가하는 API")
    public ResponseEntity<FavoritesApiRes> savePlace(@RequestBody FavoritesPlaceDto dto){
        return ResponseEntity.ok(favoritesService.savePlace(dto));
    }

    @GetMapping("/music")
    @Operation(summary = "유저가 좋아하는 음악 조회", description = "현재 로그인 한 유저가 좋아하는 음악 조회하는 API")
    public ResponseEntity<FavoritesMusicRes> getFavoritesMusics(){
        return ResponseEntity.ok(favoritesService.getFavoritesMusics());
    }

    @GetMapping("/place")
    @Operation(summary = "유저가 좋아하는 장소 조회", description = "현재 로그인 한 유저가 좋아하는 장소 조회하는 API")
    public ResponseEntity<FavoritesPlaceRes> getFavoritesPlaces(){
        return ResponseEntity.ok(favoritesService.getFavoritesPlaces());
    }

    @PostMapping("/music/delete")
    @Operation(summary = "유저가 좋아하는 음악 삭제", description = "현재 로그인 한 유저가 좋아하는 음악 삭제하는 API, trackId 주면 됌")
    public ResponseEntity<FavoritesApiRes> deleteFavoritesMusic(@RequestBody DeleteMusicReq req){
        return ResponseEntity.ok(favoritesService.deleteFavoritesMusic(req.getTrackId()));
    }

    @PostMapping("/music/delete/list")
    @Operation(summary = "유저가 좋아하는 음악들 삭제", description = "현재 로그인 한 유저가 좋아하는 음악들 삭제하는 API, trackId 리스트 주면 됌")
    public ResponseEntity<FavoritesApiRes> deleteFavoritesMusicList(@RequestBody DeleteMusicListReq req){
        return ResponseEntity.ok(favoritesService.deleteFavoritesMusicList(req.getTrackIds()));
    }

    @PostMapping("/place/delete")
    @Operation(summary = "유저가 좋아하는 장소 삭제", description = "현재 로그인 한 유저가 좋아하는 장소 삭제하는 API, contentId 주면 됌")
    public ResponseEntity<FavoritesApiRes> deleteFavoritesPlace(@RequestBody DeletePlaceReq req){
        return ResponseEntity.ok(favoritesService.deleteFavoritesPlace(req.getContentId()));
    }

    @PostMapping("/place/delete/list")
    @Operation(summary = "유저가 좋아하는 장소들 삭제", description = "현재 로그인 한 유저가 좋아하는 장소들 삭제하는 API, contentId 리스트 주면 됌")
    public ResponseEntity<FavoritesApiRes> deleteFavoritesPlace(@RequestBody DeletePlaceListReq req){
        return ResponseEntity.ok(favoritesService.deleteFavoritesPlaceList(req.getContentIds()));
    }

    @GetMapping("music/exist")
    @Operation(summary = "유저가 좋아하는 음악인지 확인", description = "현재 로그인 한 유저가 좋아하는 음악인지 확인하는 API, trackId 주면 됌")
    public ResponseEntity<ExistRes> isExistMusic(@RequestParam String trackId){
        return ResponseEntity.ok(favoritesService.isExistMusic(trackId));
    }

    @GetMapping("place/exist")
    @Operation(summary = "유저가 좋아하는 장소인지 확인", description = "현재 로그인 한 유저가 좋아하는 장소인지 확인하는 API, contentId 주면 됌")
    public ResponseEntity<ExistRes> isExistPlace(@RequestParam String contentId){
        return ResponseEntity.ok(favoritesService.isExistPlace(contentId));
    }


}
