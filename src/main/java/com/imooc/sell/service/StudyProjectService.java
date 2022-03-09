package com.imooc.sell.service;

import com.imooc.sell.dto.StudyProjectDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudyProjectService {
    StudyProjectDTO createStudyProject(StudyProjectDTO studyProjectDTO) throws Exception;

    List<StudyProjectDTO> findStudyProjectsOrderByUpdateTime(Pageable pageable);

    StudyProjectDTO findStudyByProjectId(String projectId);

    List<StudyProjectDTO> findStudyProjectByTagsLike(String keyword, Pageable pageable);

    StudyProjectDTO updateStudyProjectDTO(StudyProjectDTO studyProjectDTO);

    StudyProjectDTO increasePageviews(String projectId);

    List<StudyProjectDTO> findStudyProjectByTagsLikeOrderByUpdateTime(String keyword, Pageable pageable);

    List<StudyProjectDTO> findStudyProjectByTagsLikeOrderByFavoritesNumber(String keyword, Pageable pageable);
}
