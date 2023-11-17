package com.smalko.weather.weather.session.result;

import com.smalko.weather.weather.session.dto.ReadSessionDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SaveSessionResult {
    private final boolean successful;
    private final ReadSessionDto readSessionDto;

    public static SaveSessionResult resultSuccessful(ReadSessionDto readSessionDto){
        return new SaveSessionResult(true, readSessionDto);
    }

    public static SaveSessionResult resultUnSuccessful(){
        return  new SaveSessionResult(false, null);
    }

    private SaveSessionResult(boolean successful, ReadSessionDto readSessionDto) {
        this.successful = successful;
        this.readSessionDto = readSessionDto;
    }
}
