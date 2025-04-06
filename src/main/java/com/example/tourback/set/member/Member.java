package com.example.tourback.set.member;

import com.example.tourback.global.baseEntity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
public class Member extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;

//    @Column(unique = true, nullable = false)
//    private String email;

    private String emailVerified;

    private String phone;

    private String phoneVerified;

    private LocalDateTime lastLoginDate;
    private LocalDateTime passwordChangeDate;
    private int failedLoginAttempts;
    private String marketingConsent;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role= Role.ROLE_USER;
    public enum Role{
        ROLE_USER,ROLE_ADMIN
    }
}
