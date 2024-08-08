package com.smartcar.dororok.global.converter;

import com.smartcar.dororok.musicmode.domain.MusicMode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMusicModeConverter implements Converter<String, MusicMode> {

    @Override
    public MusicMode convert(String source) {
        return MusicMode.from(source);
    }
}