package com.example.tourback.global.jwt.refresh;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {
    Boolean existsByRefresh(String refresh);

    @Transactional(propagation= Propagation.REQUIRED, readOnly=false)
    void deleteByRefresh(String refresh);
}
