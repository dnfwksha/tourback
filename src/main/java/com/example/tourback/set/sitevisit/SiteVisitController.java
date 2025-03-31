package com.example.tourback.set.sitevisit;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sitevisit")
public class SiteVisitController {

    private final SiteVisitService siteVisitService;

    @GetMapping("/count")
    public void count() {
        siteVisitService.count();
    }
}
