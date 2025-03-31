package com.example.tourback.set.sitevisit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteVisitService {

    private final SiteVisitRepository siteVisitRepository;

    public void count() {
//        siteVisitRepository.findByVisitDate(LocalDate.now()).ifPresentOrElse(
//                siteVisit -> siteVisit.setVisitCount(siteVisit.getVisitCount() + 1),
//                () -> siteVisitRepository.save(new SiteVisit(LocalDate.now(), 1))
        Optional<SiteVisit> aa = siteVisitRepository.findByVisitDate(LocalDate.now());
        if (aa.isPresent()) {
            SiteVisit siteVisit = aa.get();
            siteVisit.setVisitCount(siteVisit.getVisitCount() + 1);
            siteVisitRepository.save(siteVisit);
        } else{
            SiteVisit siteVisit = new SiteVisit();
            siteVisit.setVisitCount(1);
            siteVisit.setVisitDate(LocalDate.now());
            siteVisitRepository.save(siteVisit);
        }
    }
}
