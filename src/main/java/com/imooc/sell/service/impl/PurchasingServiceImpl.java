package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.PurchasingProject;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.PurchasingProjectDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.PurchasingProjectRepository;
import com.imooc.sell.service.PurchasingProjectService;
import com.imooc.sell.service.UserInfoService;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PurchasingServiceImpl implements PurchasingProjectService {
    @Autowired
    PurchasingProjectRepository purchasingProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Override
    @Transactional
    public PurchasingProjectDTO createPurchasingProject(PurchasingProjectDTO purchasingProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(purchasingProjectDTO.getUserOpenid());
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(1);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        purchasingProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        PurchasingProject purchasingProject = new PurchasingProject();
        BeanUtils.copyProperties(purchasingProjectDTO,purchasingProject);
        PurchasingProject result = purchasingProjectRepository.save(purchasingProject);
        PurchasingProjectDTO resultDTO = new PurchasingProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    @Override
    public List<PurchasingProjectDTO> findPurchasingProjectsOrderByUpdateTime(Pageable pageable) {
        Page<PurchasingProject> page = purchasingProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<PurchasingProjectDTO> list = new ArrayList<>();

        for (PurchasingProject purchasingProject: page){
            PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();
            BeanUtils.copyProperties(purchasingProject, purchasingProjectDTO);
            list.add(purchasingProjectDTO);
        }
        return list;
    }

    @Override
    public PurchasingProjectDTO findPurchasingByProjectId(String projectId) {
        PurchasingProject purchasingProject = purchasingProjectRepository.findByProjectId(projectId);
        PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();
        if(purchasingProject != null) {
            BeanUtils.copyProperties(purchasingProject, purchasingProjectDTO);
            return purchasingProjectDTO;
        }
        else {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }

    }
}
