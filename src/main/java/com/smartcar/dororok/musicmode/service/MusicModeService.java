package com.smartcar.dororok.musicmode.service;

import com.smartcar.dororok.musicmode.domain.MusicModeDto;
import com.smartcar.dororok.musicmode.domain.MusicModeRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicModeService {
    private final String basicURL = System.getenv("IMAGE_BASIC_URL")+"/images/musicmode_";

    private final List<String> names = new ArrayList<>(Arrays.asList("일상","출근","퇴근","여행","드라이브","도로록 Pick!", "데이트", "친구들과"));
    private final List<String> imageUrls = new ArrayList<>(Arrays.asList("daily.png","go_to_work.png","get_off_work.png","travel.png","drive.png","dororok_pick.png", "date.png", "friends_png"));

    public MusicModeRes getMusicMode() {
        List<MusicModeDto> result = new ArrayList<>();
        for(int i = 0; i <names.size(); i++){
            result.add(MusicModeDto.builder()
                    .name(names.get(i))
                    .imageUrl(basicURL+imageUrls.get(i))
                    .build());
        }
        return MusicModeRes.builder().musicModeList(result).build();
    }
}
