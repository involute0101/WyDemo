package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProjectMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectMasterRepository extends JpaRepository<ProjectMaster, String> {
    ProjectMaster findByProjectId(String projectId);
    List<ProjectMaster> findByUserId(Integer userId);
    List<ProjectMaster> findByProjectType(Integer projectType);
    Integer deleteByProjectId(String projectId);
    List<ProjectMaster> findByUserIdAndProjectType(Integer userId, Integer projectType);

}
