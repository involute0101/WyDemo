package com.imooc.sell.service;

import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.LostPropertyProjectDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LostPropertyProjectService {
    LostPropertyProjectDTO createLostPropertyProject(LostPropertyProjectDTO lostPropertyProjectDTO) throws Exception;

    List<LostPropertyProjectDTO> findLostPropertyProjectsOrderByUpdateTime(Pageable pageable);

    LostPropertyProjectDTO findLostPropertyProjectByProjectId(String projectId);

    List<LostPropertyProjectDTO> findLostPropertyProjectByTagsLike(String keyword, Pageable pageable);

    LostPropertyProjectDTO updateLostPropertyProject(LostPropertyProjectDTO lostPropertyProjectDTO);
}
