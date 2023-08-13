package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.musical.Musical;
import com.tickettimer.backendserver.domain.musical.SiteCategory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicalRepository extends JpaRepository<Musical, String> {
    List<Musical> findBySiteCategory(SiteCategory siteCategory);

    // 뮤지컬 제목에 name을 포함 중인 뮤지컬 정보들을 가져온다.
    @Query(value = "select * from MUSICAL where title like %:name%", nativeQuery = true)
    List<Musical> findByName(@Param("name") String name);
}
