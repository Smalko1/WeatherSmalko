package com.smalko.weather.weather.entity;

import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Name("location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Users usersId;

    @JoinColumn(nullable = false)
    private Double latitude;

    @JoinColumn(nullable = false)
    private Double longitude;
}
