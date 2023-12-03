package com.smalko.weather.weather.user.result;

import com.smalko.weather.weather.location.LocationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@Getter
@EqualsAndHashCode
public class LocationsUserResult {
    private final boolean successful;
    private final Set<LocationEntity> locations;

    public static LocationsUserResult result(){
        return new LocationsUserResult(false, Set.of());
    }

    public static LocationsUserResult result(Set<LocationEntity> locations){
        return  new LocationsUserResult(true, locations);
    }

    private LocationsUserResult(boolean successful, Set<LocationEntity> locations) {
        this.successful = successful;
        this.locations = locations;
    }
}
