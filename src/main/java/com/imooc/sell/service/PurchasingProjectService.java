package com.imooc.sell.service;

import com.imooc.sell.dto.LostPropertyProjectDTO;
import com.imooc.sell.dto.PurchasingProjectDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchasingProjectService {
    PurchasingProjectDTO createPurchasingProject(PurchasingProjectDTO purchasingProjectDTO) throws Exception;
    List<PurchasingProjectDTO> findPurchasingProjectsOrderByUpdateTime(Pageable pageable);
    PurchasingProjectDTO findPurchasingByProjectId(String projectId);
}
