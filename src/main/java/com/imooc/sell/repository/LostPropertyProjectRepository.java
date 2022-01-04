package com.imooc.sell.repository;

import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dataobject.LostPropertyProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LostPropertyProjectRepository extends JpaRepository<LostPropertyProject, Integer> {
    LostPropertyProject findByProjectId(String projectId);
    Page<LostPropertyProject> findByOrderByUpdateTimeDesc(Pageable pageable);
    List<LostPropertyProject> findByTitle(String title);
    List<LostPropertyProject> findByLocation(String location);

}
