package com.smalko.weather.weather.entity;

import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.ALL;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "locations")
@EqualsAndHashCode(exclude = "locations")
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(nullable = false, unique = true)
    private String username;
    @JoinColumn(nullable = false)
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "users", orphanRemoval = true)
    @Cascade(ALL)
    private Set<Location> locations = new HashSet<>();

    public void addLocation(Location location){
        locations.add(location);
        location.setUsers(this);
    }
}
