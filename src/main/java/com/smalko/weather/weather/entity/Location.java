package com.smalko.weather.weather.entity;

import com.smalko.weather.weather.user.UsersEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location", indexes = {
        @Index(name = "idx_location_coords", columnList = "latitude, longitude")
})
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity usersEntity;

    @JoinColumn(nullable = false)
    private Double latitude;

    @JoinColumn(nullable = false)
    private Double longitude;
}
