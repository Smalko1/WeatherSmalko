package com.smalko.weather.weather.location.mapper;

import com.smalko.weather.weather.location.dto.CreateLocationDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationMapperTest {

    @Test
    void createLocationDtoToLocationEntity() {
        var locationDto = CreateLocationDto.builder()
                .name("Kiev")
                .longitude(22.1)
                .latitude(23.1)
                .build();

        var locationEntity = LocationMapper.INSTANCE.createLocationDtoToLocationEntity(locationDto);

        Assertions.assertThat(locationEntity).isNotNull();
        Assertions.assertThat(locationEntity.getName()).isEqualTo(locationDto.getName());
        Assertions.assertThat(locationEntity.getLongitude()).isEqualTo(locationDto.getLongitude());
        Assertions.assertThat(locationEntity.getLatitude()).isEqualTo(locationDto.getLongitude());
    }
}