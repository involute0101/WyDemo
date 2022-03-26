package com.imooc.sell.service;

import com.imooc.sell.controller.form.StudyProjectFrom;
import com.imooc.sell.dto.StudyProjectDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudyProjectService {
    StudyProjectDTO createStudyProject(StudyProjectDTO studyProjectDTO) throws Exception;

    List<StudyProjectDTO> findStudyProjectsOrderByUpdateTime(Pageable pageable);

    List<StudyProjectDTO> findStudyProjectsOrderByFavoritesNumber(Pageable pageable);

    StudyProjectDTO findStudyByProjectId(String projectId);

    List<StudyProjectDTO> findStudyProjectByTagsLike(String keyword, Pageable pageable);

    List<StudyProjectDTO> findStudyProjectByTitleLike(String titleKeyword,Pageable pageable);

    List<StudyProjectDTO> findByComplexService(Pageable pageable);

    List<StudyProjectDTO> findStudyProjectOrderByAmount(Pageable pageable, String sort);

    StudyProjectDTO updateStudyProjectDTO(StudyProjectDTO studyProjectDTO);

    StudyProjectDTO increasePageviews(String projectId);

    List<StudyProjectDTO> findStudyProjectByTagsLikeOrderByUpdateTime(String keyword, Pageable pageable);

    List<StudyProjectDTO> findStudyProjectByTagsLikeOrderByFavoritesNumber(String keyword, Pageable pageable);

    void tagHandler(StudyProjectFrom studyProjectFrom);
}
