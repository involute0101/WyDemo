package com.imooc.sell.service;

import com.imooc.sell.dataobject.PurchasingProject;
import com.imooc.sell.dto.LostPropertyProjectDTO;
import com.imooc.sell.dto.PurchasingProjectDTO;
import com.imooc.sell.dto.RewardProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchasingProjectService {
    PurchasingProjectDTO createPurchasingProject(PurchasingProjectDTO purchasingProjectDTO) throws Exception;

    List<PurchasingProjectDTO> findPurchasingProjectsOrderByUpdateTime(Pageable pageable);

    PurchasingProjectDTO findPurchasingByProjectId(String projectId);

    List<PurchasingProjectDTO> findPurchasingProjectByTagsLike(String keyword, Pageable pageable);

    List<PurchasingProjectDTO> findPurchasingProjectOrderByAmount(Pageable pageable, String sort);

    List<PurchasingProjectDTO> findByPurchasingProjectByTitleLike(String titleKeyword, Pageable pageable);

    PurchasingProjectDTO updatePurchasingProject(PurchasingProjectDTO purchasingProjectDTO);

    List<PurchasingProjectDTO> findPurchasingProjectOrderByFavoritesNumber(Pageable pageable);

    List<PurchasingProjectDTO> findByComplexService(Pageable pageable);

    PurchasingProjectDTO increasePageviews(String projectId);

    List<PurchasingProjectDTO> findPurchasingProjectByTagsLikeOrderByUpdateTime(String keyword, Pageable pageable);

    List<PurchasingProjectDTO> findPurchasingProjectByTagsLikeOrderByFavoritesNumber(String keyword, Pageable pageable);
}
