package com.imooc.sell.service;

import com.imooc.sell.controller.form.IdleProjectFrom;
import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dto.IdleProjectDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IdleProjectService {
    IdleProjectDTO createIdleProjectOne(IdleProjectDTO idleProjectDTO) throws Exception;

    List<IdleProjectDTO> findIdleProjectsOrderByUpdateTime(Pageable pageable);

    IdleProjectDTO findIdleProjectByProjectId(String projectId);

    List<IdleProjectDTO> findIdleProjectByTagsLike(String keyword, Pageable pageable);

    List<IdleProjectDTO> findIdleProjectOrderByAmount(Pageable pageable, String sort);

    List<IdleProjectDTO> findIdleProjectByTitleLike(String titleKeyword, Pageable pageable);

    IdleProjectDTO updateIdleProjectDTO(IdleProjectDTO idleProjectDTO);

    List<IdleProjectDTO> findIdleProjectOrderByFavoritesNumber(Pageable pageable);

    IdleProjectDTO increasePageviews(String projectId);

    List<IdleProjectDTO> findByComplexService(Pageable pageable);

    List<IdleProjectDTO> findIdleProjectByTagsLikeOrderByUpdateTime(String keyword,Pageable pageable);

    List<IdleProjectDTO> findIdleProjectByTagsLikeOrderByFavoritesNumber(String keyword, Pageable pageable);

    void tagHandler(IdleProjectFrom idleProjectFrom);
}
