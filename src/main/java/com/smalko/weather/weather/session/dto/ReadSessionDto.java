package com.smalko.weather.weather.session.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReadSessionDto {
    private Integer id;
    private LocalDateTime expiresAt;
}
