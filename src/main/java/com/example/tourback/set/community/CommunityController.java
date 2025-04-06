package com.example.tourback.set.community;

import com.example.tourback.set.community.querydsl.CommunityQueryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("/create")
    public void create(@Valid CommunityDto communityDto) throws IOException {
        System.out.println("communityDto : "+communityDto);
        communityService.create(communityDto);
    }

    @GetMapping("/all")
    public List<CommunityQueryDto> getAll() {
        return communityService.findAllCommunity();
    }

//    @PostMapping("/viewcount/{id}")
//    public void viewCount(@PathVariable("id") String communityCode) {
//        communityService.viewCount(communityCode);
//    }

    @PostMapping("/one/{id}")
    public CommunityQueryDto findOne(@PathVariable("id") String communityCode) {
        System.out.println("여긴 오지?");
        return communityService.findOne(communityCode);
    }
}
