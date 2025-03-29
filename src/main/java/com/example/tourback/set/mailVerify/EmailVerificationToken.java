package com.example.tourback.set.mailVerify;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String email;
    private LocalDateTime expiryDate;
    private boolean verified;

    public EmailVerificationToken(String email, String token) {
        this.token = token;
        this.email = email;
        this.expiryDate = LocalDateTime.now().plusMinutes(5); // 24시간 유효
        this.verified = false;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}