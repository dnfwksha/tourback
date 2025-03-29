package com.example.tourback.global.jwt.refresh;

import com.example.tourback.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class RefreshEntity extends BaseEntity {

    private String username;
    private String refresh;
    private String expiration;
}
