package com.imooc.sell.repository;

import com.imooc.sell.dataobject.RewardProject;
import com.imooc.sell.dataobject.StudyProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StudyProjectRepository extends JpaRepository<StudyProject, Integer> {
    StudyProject findByProjectId(String projectId);
    Page<StudyProject> findByOrderByUpdateTimeDesc(Pageable pageable);
    List<StudyProject> findByTitle(String title);
    List<StudyProject> findByLocation(String location);

}
