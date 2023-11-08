package com.smalko.weather.weather.user.result;

import com.smalko.weather.weather.location.Location;
import lombok.Getter;

import java.util.Set;

@Getter
public class LocationsUserResult {
    private final boolean successful;
    private final Set<Location> locations;

    public static LocationsUserResult result(){
        return new LocationsUserResult(false, Set.of());
    }

    public static LocationsUserResult result(Set<Location> locations){
        return  new LocationsUserResult(true, locations);
    }

    private LocationsUserResult(boolean successful, Set<Location> locations) {
        this.successful = successful;
        this.locations = locations;
    }
}
