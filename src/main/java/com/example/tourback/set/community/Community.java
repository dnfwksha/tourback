package com.example.tourback.set.community;

import com.example.tourback.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Community extends BaseEntity {

    private String communityCode;
    private String title;
    private String author;
    private int viewCount = 0;
    private String status;
}
