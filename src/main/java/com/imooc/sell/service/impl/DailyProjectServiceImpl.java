package com.imooc.sell.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.controller.form.DailyProjectForm;
import com.imooc.sell.controller.form.MakeFriendProjectForm;
import com.imooc.sell.controller.form.TagForm;
import com.imooc.sell.dataobject.DailyProject;
import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dto.*;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.DailyProjectRepository;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DailyProjectServiceImpl {
    public static final Logger logger = LoggerFactory.getLogger(MakeFriendProjectServiceImpl.class);

    @Autowired
    DailyProjectRepository dailyProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Autowired
    TagService tagService;

    /**
     * 创建 日常项目
     * @param dailyProjectDTO
     * @return
     * @throws Exception
     */
    @Transactional
    public DailyProjectDTO createDailyProject(DailyProjectDTO dailyProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(dailyProjectDTO.getUserOpenId());
        if(userInfoDTO==null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(8);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        dailyProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        DailyProject dailyProject = new DailyProject();
        BeanUtils.copyProperties(dailyProjectDTO,dailyProject,"picture");
        String pictureArray = "";
        if(dailyProjectDTO.getPicture()!=null){
            for(String picture : dailyProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            dailyProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("创建交友项目:"+dailyProject.toString());
        DailyProject save = dailyProjectRepository.save(dailyProject);
        DailyProjectDTO result = new DailyProjectDTO();
        BeanUtils.copyProperties(save,result,"picture");
        if(!StringUtils.isEmpty(dailyProject.getPicture())){
            result.setPicture(dailyProject.getPicture().split(","));
        }
        return result;
    }

    /**
     * 处理标签（向上兼容），不存在则创建
     * @param dailyProjectForm
     */
    public void tagHandler(DailyProjectForm dailyProjectForm) {
        TagForm tagForm = new TagForm();
        tagForm.setUserOpenId(dailyProjectForm.getOpenid());
        tagForm.setTagContent(dailyProjectForm.getTags());
        tagService.createTag(tagForm,1);
    }

    /**
     * 按照更新时间查询 日常项目
     * @param pageable
     * @return
     */
    public List<DailyProjectDTO> findDailyProjectsOrderByUpdateTime(Pageable pageable) {
        Page<DailyProject> page = dailyProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<DailyProjectDTO> list = new ArrayList<>();
        for (DailyProject dailyProject: page){
            DailyProjectDTO dailyProjectDTO = new DailyProjectDTO();
            BeanUtils.copyProperties(dailyProject, dailyProjectDTO,"picture");
            if (dailyProject.getPicture()!=null){
                dailyProjectDTO.setPicture(dailyProject.getPicture().split(","));
            }
            list.add(dailyProjectDTO);
        }
        return list;
    }

    /**
     * 按照热度查询
     * @param pageable 分页查询
     * @return
     */
    public List<DailyProjectDTO> findDailyProjectOrderByFavoritesNumber(Pageable pageable) {
        Page<DailyProject> page = dailyProjectRepository.findByOrderByFavoriteNumberDesc(pageable);
        List<DailyProjectDTO> list = new ArrayList<>();
        for (DailyProject dailyProject: page){
            DailyProjectDTO dailyProjectDTO = new DailyProjectDTO();
            BeanUtils.copyProperties(dailyProject, dailyProjectDTO,"picture");
            if (dailyProject.getPicture()!=null){
                dailyProjectDTO.setPicture(dailyProject.getPicture().split(","));
            }
            list.add(dailyProjectDTO);
        }
        return list;
    }

    /**
     * 综合排序
     * @param pageable 分页请求
     * @return
     */
    public List<DailyProjectDTO> findByComplexService(Pageable pageable) {
        List<DailyProjectDTO> result = new ArrayList<>();
        int pageSize = pageable.getPageSize();
        int offsetNumber = pageable.getOffset();
        List<DailyProject> list = dailyProjectRepository.findByComplex(pageSize, offsetNumber);
        for(DailyProject dailyProject : list){
            DailyProjectDTO dailyProjectDTO = new DailyProjectDTO();
            BeanUtils.copyProperties(dailyProject, dailyProjectDTO,"picture");
            if (dailyProject.getPicture()!=null){
                dailyProjectDTO.setPicture(dailyProject.getPicture().split(","));
            }
            result.add(dailyProjectDTO);
        }
        return result;
    }

    /**
     * 按照标题关键字查询
     * @param titleKeyword 标题关键字
     * @param pageable  分页请求
     * @return
     */
    public List<DailyProjectDTO> findByTitleLike(String titleKeyword,Pageable pageable){
        List<DailyProjectDTO> list = new ArrayList<>();
        Page<DailyProject> page = dailyProjectRepository.findByTitleLike("%" + titleKeyword + "%", pageable);
        for (DailyProject dailyProject: page){
            DailyProjectDTO dailyProjectDTO = new DailyProjectDTO();
            BeanUtils.copyProperties(dailyProject, dailyProjectDTO,"picture");
            if (dailyProject.getPicture()!=null){
                dailyProjectDTO.setPicture(dailyProject.getPicture().split(","));
            }
            list.add(dailyProjectDTO);
        }
        return list;
    }

    /**
     * 根据id查找项目
     * @param projectId
     * @return
     */
    public DailyProjectDTO findRewardByProjectId(String projectId) {
        DailyProject dailyProject = dailyProjectRepository.findByProjectId(projectId);
        DailyProjectDTO dailyProjectDTO = new DailyProjectDTO();
        if(dailyProject!=null){
            BeanUtils.copyProperties(dailyProject, dailyProjectDTO,"picture");
            if (dailyProject.getPicture()!=null){
                dailyProjectDTO.setPicture(dailyProject.getPicture().split(","));
            }
            return dailyProjectDTO;
        }else{
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }
    }

    /**
     * 增加浏览量
     * @param projectId
     * @return
     */
    public DailyProjectDTO increasePageviews(String projectId) {
        DailyProject dailyProject = dailyProjectRepository.findByProjectId(projectId);
        if(dailyProject==null){
            throw new SellException(ResultEnum.PROJECT_ID_NOT_FOUND);
        }
        dailyProject.setPageviews(dailyProject.getPageviews()+1);
        dailyProjectRepository.save(dailyProject);
        DailyProjectDTO dailyProjectDTO = new DailyProjectDTO();
        BeanUtils.copyProperties(dailyProject, dailyProjectDTO,"picture");
        if(dailyProject.getPicture()!=null){
            dailyProjectDTO.setPicture(dailyProject.getPicture().split(","));
        }
        return dailyProjectDTO;
    }
}
