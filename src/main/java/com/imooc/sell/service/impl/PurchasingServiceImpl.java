package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.LostPropertyProject;
import com.imooc.sell.dataobject.PurchasingProject;
import com.imooc.sell.dto.LostPropertyProjectDTO;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public static final Logger logger = LoggerFactory.getLogger(PurchasingServiceImpl.class);

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
        BeanUtils.copyProperties(purchasingProjectDTO,purchasingProject,"picture");
        String pictureArray = "";
        if(purchasingProjectDTO.getPicture()!=null){
            for(String picture : purchasingProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            purchasingProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("创建跑腿项目："+purchasingProject.toString());
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
            BeanUtils.copyProperties(purchasingProject, purchasingProjectDTO,"picture");
            if(purchasingProject.getPicture()!=null){
                purchasingProjectDTO.setPicture(purchasingProject.getPicture().split(","));
            }
            list.add(purchasingProjectDTO);
        }
        return list;
    }

    @Override
    public PurchasingProjectDTO findPurchasingByProjectId(String projectId) {
        PurchasingProject purchasingProject = purchasingProjectRepository.findByProjectId(projectId);
        PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();
        if(purchasingProject != null) {
            BeanUtils.copyProperties(purchasingProject, purchasingProjectDTO,"picture");
            if(purchasingProject.getPicture()!=null){
                purchasingProjectDTO.setPicture(purchasingProject.getPicture().split(","));
            }
            return purchasingProjectDTO;
        }
        else {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }

    }

    @Override
    public List<PurchasingProjectDTO> findPurchasingProjectByTagsLike(String keyword, Pageable pageable) {
        List<PurchasingProjectDTO> list = new ArrayList<>();
        Page<PurchasingProject> page = purchasingProjectRepository.findByTagsLike("%" + keyword + "%", pageable);
        for(PurchasingProject purchasingProject: page){
            PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();
            BeanUtils.copyProperties(purchasingProject,purchasingProjectDTO,"picture");
            if(purchasingProject.getPicture()!=null){
                purchasingProjectDTO.setPicture(purchasingProject.getPicture().split(","));
            }
            list.add(purchasingProjectDTO);
        }
        return list;
    }

    /**
     * 根据悬赏金额大小排序，查询闲置项目
     * @param pageable 分页请求
     * @param sort 排序方式
     * @return
     */
    @Override
    public List<PurchasingProjectDTO> findPurchasingProjectOrderByAmount(Pageable pageable, String sort) {
        Page<PurchasingProject> page = null;
        if("desc".equals(sort))page = purchasingProjectRepository.findByOrderByAmountDesc(pageable);
        else page = purchasingProjectRepository.findByOrderByAmount(pageable);
        List<PurchasingProjectDTO> arrayList = new ArrayList<>();
        for(PurchasingProject purchasingProject : page){
            PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();
            BeanUtils.copyProperties(purchasingProject,purchasingProjectDTO,"picture");
            if(purchasingProject.getPicture()!=null){
                purchasingProjectDTO.setPicture(purchasingProject.getPicture().split(","));
            }
            arrayList.add(purchasingProjectDTO);
        }
        return arrayList;
    }
}
