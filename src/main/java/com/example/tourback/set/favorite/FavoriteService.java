package com.example.tourback.set.favorite;

import com.example.tourback.global.SecurityUtils;
import com.example.tourback.set.favorite.querydsl.FavoriteQueryDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final SecurityUtils securityUtils;

    public List<FavoriteQueryDto> findAllFavorite() {
        return favoriteRepository.findAllFavorite(securityUtils.getCurrentUsername());
    }
    @Transactional
    public void create(String productCode) {
        Favorite favorite = new Favorite();
        favorite.setUsername(securityUtils.getCurrentUsername());
        favorite.setProductCode(productCode);
        favoriteRepository.save(favorite);
    }

    public void delete(String productCode) {
        favoriteRepository.deleteByProductCodeAndUsername(productCode, securityUtils.getCurrentUsername());
    }

    public boolean check(String productCode) {
        return favoriteRepository.existsByProductCodeAndUsername(productCode, securityUtils.getCurrentUsername());
    }
}
