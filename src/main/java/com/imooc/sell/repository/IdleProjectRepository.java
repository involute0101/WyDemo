package com.imooc.sell.repository;

import com.imooc.sell.dataobject.IdleProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface IdleProjectRepository extends JpaRepository<IdleProject, Integer> {
    IdleProject findByProjectId(String projectId);
    Page<IdleProject> findByOrderByUpdateTimeDesc(Pageable pageable);
    List<IdleProject> findByTitle(String title);
    List<IdleProject> findByLocation(String location);

}
