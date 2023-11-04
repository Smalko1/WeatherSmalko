package com.smalko.weather.weather.session.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ReadSessionDto {
    private UUID id;
    private LocalDateTime expiresAt;
}
