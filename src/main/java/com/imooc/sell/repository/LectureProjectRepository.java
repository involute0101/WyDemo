package com.imooc.sell.repository;

import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dataobject.LectureProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface LectureProjectRepository extends JpaRepository<LectureProject, Integer> {
    LectureProject findByProjectId(String projectId);
    Page<LectureProject> findByOrderByUpdateTimeDesc(Pageable pageable);
    List<LectureProject> findByTitle(String title);
    List<LectureProject> findByLocation(String location);

}
