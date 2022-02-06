package com.imooc.sell.repository;

import com.imooc.sell.dataobject.PurchasingProject;
import com.imooc.sell.dataobject.RewardProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RewardProjectRepository extends JpaRepository<RewardProject, Integer> {
    RewardProject findByProjectId(String projectId);
    Page<RewardProject> findByOrderByUpdateTimeDesc(Pageable pageable);
    Page<RewardProject> findByOrderByAmount(Pageable pageable);
    Page<RewardProject> findByOrderByAmountDesc(Pageable pageable);
    List<RewardProject> findByTitle(String title);
    List<RewardProject> findByLocation(String location);

}
