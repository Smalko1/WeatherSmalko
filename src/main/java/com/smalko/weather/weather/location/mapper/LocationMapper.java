package com.smalko.weather.weather.location.mapper;

import com.smalko.weather.weather.location.LocationEntity;
import com.smalko.weather.weather.location.dto.CreateLocationDto;
import com.smalko.weather.weather.location.dto.ReadSearchCityDto;
import com.smalko.weather.weather.location.json.SearchCityList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    ReadSearchCityDto searchCityToReadSearchCityDto(SearchCityList searchCityList);
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "id", ignore = true)
    LocationEntity createLocationDtoToLocationEntity(CreateLocationDto createLocationDto);
}
