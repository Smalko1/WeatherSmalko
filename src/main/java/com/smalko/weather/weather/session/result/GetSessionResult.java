package com.smalko.weather.weather.session.result;

import com.smalko.weather.weather.user.dto.ReadUserDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class GetSessionResult {
    private final boolean successful;
    private final ReadUserDto readUserDto;

    public static GetSessionResult result() {
        return new GetSessionResult(false, null);
    }

    public static GetSessionResult result(ReadUserDto readUserDto) {
        return new GetSessionResult(true, readUserDto);
    }

    private GetSessionResult(boolean successful,ReadUserDto readUserDto) {
        this.successful = successful;
        this.readUserDto = readUserDto;
    }
}
