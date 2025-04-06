package com.example.tourback.set.favorite;

import com.example.tourback.set.favorite.querydsl.FavoriteQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/all")
    public List<FavoriteQueryDto> getAll() {
        return favoriteService.findAllFavorite();
    }

    @PostMapping("/create/{id}")
    public void create(@PathVariable("id") String productCode) {
        favoriteService.create(productCode);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String productCode) {
        favoriteService.delete(productCode);
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<?> check(@PathVariable("id") String productCode) {
        boolean isFavorite = favoriteService.check(productCode);
        System.out.println("isFavorite");
        System.out.println(isFavorite);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isFavorite", isFavorite);
        return ResponseEntity.ok(response);
    }

}
