package com.example.tourback.set.favorite.querydsl;

import java.util.List;

public interface FavoriteRepositoryCustom {

    List<FavoriteQueryDto> findAllFavorite(String currentUsername);
}
