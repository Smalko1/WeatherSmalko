package com.smalko.weather.weather.session;

import com.smalko.weather.weather.user.UsersEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(generator = "BINARY(16)")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UsersEntity usersEntity;

    private LocalDateTime expiresAt;
}
