package com.imooc.sell.repository;

import com.imooc.sell.dataobject.StudyProject;
import com.imooc.sell.dataobject.WyMissionProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WyMissionProjectRepository extends JpaRepository<WyMissionProject, Integer> {
    WyMissionProject findByProjectId(String projectId);
    Page<WyMissionProject> findByOrderByUpdateTimeDesc(Pageable pageable);
    List<WyMissionProject> findByTitle(String title);
    List<WyMissionProject> findByLocation(String location);

}
