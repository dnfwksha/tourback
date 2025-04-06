package com.example.tourback.set.favorite;

import com.example.tourback.global.baseEntity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Favorite extends BaseEntity {

    @Column(unique = true)
    private String favoriteCode;
    private String username;
    private String productCode;
}
