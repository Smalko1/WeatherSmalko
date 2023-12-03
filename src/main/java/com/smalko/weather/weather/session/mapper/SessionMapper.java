package com.smalko.weather.weather.session.mapper;

import com.smalko.weather.weather.session.SessionEntity;
import com.smalko.weather.weather.session.dto.CreateSessionDto;
import com.smalko.weather.weather.session.dto.ReadSessionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessionMapper {
    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    @Mapping(target = "id", ignore = true)
    SessionEntity createSessionDtoToSessionEntity(CreateSessionDto createSessionDto);

    ReadSessionDto sessionEntityToReadSessionDto(SessionEntity session);
}
