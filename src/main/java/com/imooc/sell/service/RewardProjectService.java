package com.imooc.sell.service;

import com.imooc.sell.dto.PurchasingProjectDTO;
import com.imooc.sell.dto.RewardProjectDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RewardProjectService {
    RewardProjectDTO createRewardProject(RewardProjectDTO rewardProjectDTO) throws Exception;
    List<RewardProjectDTO> findRewardProjectsOrderByUpdateTime(Pageable pageable);
    RewardProjectDTO findRewardByProjectId(String projectId);
}