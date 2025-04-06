package com.example.tourback.set.community.image;

import com.example.tourback.global.baseEntity.ImageBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class CommunityImage extends ImageBaseEntity {

    private String communityCode;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    public enum ImageType {
        COMMUNITY
    }
}
