package com.example.tourback.set.region;

import com.example.tourback.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Region extends BaseEntity {
    private String name;
    private Integer displayOrder;

//    @ManyToMany(mappedBy = "regions")
//    private Set<Product> products = new HashSet<>();
}
