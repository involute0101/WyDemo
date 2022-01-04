package com.imooc.sell.service;

import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.LectureProjectDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LectureProjectService {
    LectureProjectDTO createLectureProjectOne(LectureProjectDTO lectureProjectDTO) throws Exception;
    List<LectureProjectDTO> findLectureProjectsOrderByUpdateTime(Pageable pageable);
    LectureProjectDTO findLectureProjectByProjectId(String projectId);
}
