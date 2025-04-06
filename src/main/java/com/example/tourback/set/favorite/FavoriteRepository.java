package com.example.tourback.set.favorite;

import com.example.tourback.set.favorite.querydsl.FavoriteRepositoryCustom;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>, FavoriteRepositoryCustom {
    @Transactional
    void deleteByProductCodeAndUsername(String productCode, String currentUsername);

    boolean existsByProductCodeAndUsername(String productCode, String currentUsername);
}
