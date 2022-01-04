package com.imooc.sell.service;

import com.imooc.sell.dto.StudyProjectDTO;
import com.imooc.sell.dto.WyMissionProjectDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WyMissionProjectService {
    WyMissionProjectDTO createWyMissionProject(WyMissionProjectDTO wyMissionProjectDTO) throws Exception;
    List<WyMissionProjectDTO> findWyMissionProjectsOrderByUpdateTime(Pageable pageable);
    WyMissionProjectDTO findWyMissionByProjectId(String projectId);
}
