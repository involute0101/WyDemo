package com.imooc.sell.service;

import com.imooc.sell.controller.form.LostPropertyProjectFrom;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.LostPropertyProjectDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LostPropertyProjectService {
    LostPropertyProjectDTO createLostPropertyProject(LostPropertyProjectDTO lostPropertyProjectDTO) throws Exception;

    List<LostPropertyProjectDTO> findLostPropertyProjectsOrderByUpdateTime(Pageable pageable);

    LostPropertyProjectDTO findLostPropertyProjectByProjectId(String projectId);

    List<LostPropertyProjectDTO> findLostPropertyProjectByTagsLike(String keyword, Pageable pageable);

    List<LostPropertyProjectDTO> findLostPropertyProjectByTitleLike(String titleKeyword,Pageable pageable);

    List<LostPropertyProjectDTO> findLostPropertyProjectOrderByFavoritesNumber(Pageable pageable);

    List<LostPropertyProjectDTO> findLostPropertyProjectOrderByAmount(Pageable pageable, String sort);

    List<LostPropertyProjectDTO> findByComplexService(Pageable pageable);

    LostPropertyProjectDTO updateLostPropertyProject(LostPropertyProjectDTO lostPropertyProjectDTO);

    LostPropertyProjectDTO increasePageviews(String projectId);

    List<LostPropertyProjectDTO> findLostPropertyProjectByTagsLikeOrderByUpdateTime(String keyword,Pageable pageable);

    List<LostPropertyProjectDTO> findLostPropertyProjectByTagsLikeOrderByFavoritesNumber(String keyword, Pageable pageable);

    void tagHandler(LostPropertyProjectFrom lostPropertyProjectFrom);
}
