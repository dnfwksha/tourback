package com.example.tourback.set.sitevisit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SiteVisitRepository extends JpaRepository<SiteVisit,Long> {
    Optional<SiteVisit> findByVisitDate(LocalDate now);
}
