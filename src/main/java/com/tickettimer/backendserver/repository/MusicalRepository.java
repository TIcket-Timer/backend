package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicalRepository extends JpaRepository<Musical, String> {
    public List<Musical> findBySiteCategory(String siteCategory);
}
