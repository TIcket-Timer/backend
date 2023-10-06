package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicalRepository extends JpaRepository<Musical, String> {
    List<Musical> findBySiteCategory(SiteCategory siteCategory, Pageable pageable);

    // 뮤지컬 제목에 name을 포함 중인 뮤지컬 정보들을 가져온다.
    @Query(value = "select * from MUSICAL where title like %:name% and site_category=:siteCategory", nativeQuery = true)
    List<Musical> findByNameAndSiteCategory(@Param("name") String name, @Param("siteCategory") String siteCategory, Pageable pageable);

    Page<Musical> findAll(Pageable pageable);
    Page<Musical> findAllByOrderByCreatedTimeDesc(Pageable pageable);

}
