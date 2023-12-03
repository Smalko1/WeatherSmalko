package com.smalko.weather.weather.user;

import com.smalko.weather.weather.location.LocationEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
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
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "users", orphanRemoval = true)
    @Cascade(ALL)
    private Set<LocationEntity> locations = new HashSet<>();

    public void addLocation(LocationEntity location){
        locations.add(location);
        location.setUsers(this);
    }

    public void removeLocation(LocationEntity location) {
        locations.remove(location);
        location.setUsers(null);
    }
}
