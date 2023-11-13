package com.smalko.weather.weather.location.mapper;

import com.smalko.weather.weather.location.dto.ReadSearchCityDto;
import com.smalko.weather.weather.location.json.SearchCity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    SearchCity searchCityToReadSearchCityDto(ReadSearchCityDto readSearchCityDto);
}
