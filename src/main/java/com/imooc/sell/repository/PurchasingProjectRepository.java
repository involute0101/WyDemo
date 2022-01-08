package com.imooc.sell.repository;

import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dataobject.PurchasingProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PurchasingProjectRepository extends JpaRepository<PurchasingProject, Integer> {
    PurchasingProject findByProjectId(String projectId);
    Page<PurchasingProject> findByOrderByUpdateTimeDesc(Pageable pageable);
    List<PurchasingProject> findByTitle(String title);
    List<PurchasingProject> findByLocation(String location);

}