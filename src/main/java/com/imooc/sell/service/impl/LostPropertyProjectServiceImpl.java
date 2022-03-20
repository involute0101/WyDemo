package com.imooc.sell.service.impl;

import com.imooc.sell.controller.form.LostPropertyProjectFrom;
import com.imooc.sell.controller.form.TagForm;
import com.imooc.sell.dataobject.JobsProject;
import com.imooc.sell.dataobject.LostPropertyProject;
import com.imooc.sell.dto.JobsProjectDTO;
import com.imooc.sell.dto.LostPropertyProjectDTO;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.LostPropertyProjectRepository;
import com.imooc.sell.service.LostPropertyProjectService;
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
public class LostPropertyProjectServiceImpl implements LostPropertyProjectService {

    public static final Logger logger = LoggerFactory.getLogger(LostPropertyProjectServiceImpl.class);

    @Autowired
    LostPropertyProjectRepository lostPropertyProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Autowired
    TagService tagService;

    @Override
    @Transactional
    public LostPropertyProjectDTO createLostPropertyProject(LostPropertyProjectDTO lostPropertyProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(lostPropertyProjectDTO.getUserOpenid());
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(5);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        lostPropertyProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        LostPropertyProject lostPropertyProject = new LostPropertyProject();
        BeanUtils.copyProperties(lostPropertyProjectDTO,lostPropertyProject,"picture");
        String pictureArray = "";
        if(lostPropertyProjectDTO.getPicture()!=null){
            for(String picture : lostPropertyProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            lostPropertyProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("创建失物招领项目："+lostPropertyProject.toString());
        LostPropertyProject result = lostPropertyProjectRepository.save(lostPropertyProject);
        LostPropertyProjectDTO resultDTO = new LostPropertyProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    @Override
    public List<LostPropertyProjectDTO> findLostPropertyProjectsOrderByUpdateTime(Pageable pageable) {
        Page<LostPropertyProject> page = lostPropertyProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<LostPropertyProjectDTO> list = new ArrayList<>();

        for (LostPropertyProject lostPropertyProject: page){
            LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();
            BeanUtils.copyProperties(lostPropertyProject, lostPropertyProjectDTO,"picture");
            if(lostPropertyProject.getPicture()!=null){
                lostPropertyProjectDTO.setPicture(lostPropertyProject.getPicture().split(","));
            }
            list.add(lostPropertyProjectDTO);
        }
        return list;
    }


    @Override
    public LostPropertyProjectDTO findLostPropertyProjectByProjectId(String projectId) {
        LostPropertyProject lostPropertyProject = lostPropertyProjectRepository.findByProjectId(projectId);
        LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();
        if(lostPropertyProject != null) {
            BeanUtils.copyProperties(lostPropertyProject, lostPropertyProjectDTO,"picture");
            if(lostPropertyProject.getPicture()!=null){
                lostPropertyProjectDTO.setPicture(lostPropertyProject.getPicture().split(","));
            }
            return lostPropertyProjectDTO;
        }
        else {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }
    }

    @Override
    public List<LostPropertyProjectDTO> findLostPropertyProjectByTagsLike(String keyword, Pageable pageable) {
        List<LostPropertyProjectDTO> list = new ArrayList<>();
        Page<LostPropertyProject> page = lostPropertyProjectRepository.findByTagsLike("%" + keyword + "%", pageable);
        for(LostPropertyProject lostPropertyProject: page){
            LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();
            BeanUtils.copyProperties(lostPropertyProject,lostPropertyProjectDTO,"picture");
            if(lostPropertyProject.getPicture()!=null){
                lostPropertyProjectDTO.setPicture(lostPropertyProject.getPicture().split(","));
            }
            list.add(lostPropertyProjectDTO);
        }
        return list;
    }

    /**
     * 根据收藏数降序，查询最热的项目
     * @param pageable 分页请求
     * @return
     */
    @Override
    public List<LostPropertyProjectDTO> findLostPropertyProjectOrderByFavoritesNumber(Pageable pageable) {
        Page<LostPropertyProject> page = lostPropertyProjectRepository.findByOrderByFavoriteNumberDesc(pageable);
        List<LostPropertyProjectDTO> list = new ArrayList<>();
        for(LostPropertyProject lostPropertyProject : page){
            LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();
            BeanUtils.copyProperties(lostPropertyProject,lostPropertyProjectDTO,"picture");
            if(lostPropertyProject.getPicture()!=null){
                lostPropertyProjectDTO.setPicture(lostPropertyProject.getPicture().split(","));
            }
            list.add(lostPropertyProjectDTO);
        }
        return list;
    }

    /**
     * 根据悬赏金额大小排序，查询悬赏项目
     * @param pageable
     * @param sort 升序（asc）降序（desc）方式
     * @return
     */
    @Override
    public List<LostPropertyProjectDTO> findLostPropertyProjectOrderByAmount(Pageable pageable, String sort) {
        Page<LostPropertyProject> page = null;
        if("desc".equals(sort))page = lostPropertyProjectRepository.findByOrderByAmountDesc(pageable);
        else page = lostPropertyProjectRepository.findByOrderByAmount(pageable);
        List<LostPropertyProjectDTO> list = new ArrayList<>();
        for(LostPropertyProject lostPropertyProject : page){
            LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();
            BeanUtils.copyProperties(lostPropertyProject,lostPropertyProjectDTO,"picture");
            if(lostPropertyProject.getPicture()!=null){
                lostPropertyProjectDTO.setPicture(lostPropertyProject.getPicture().split(","));
            }
            list.add(lostPropertyProjectDTO);
        }
        return list;
    }

    /**
     * 综合排序查找，权重=时间戳*0.000001+点赞数*10
     * @param pageable  分页请求
     * @return
     */
    @Override
    public List<LostPropertyProjectDTO> findByComplexService(Pageable pageable) {
        List<LostPropertyProjectDTO> result = new ArrayList<>();
        int pageSize = pageable.getPageSize();
        int offsetNumber = pageable.getOffset();
        List<LostPropertyProject> lostPropertyProjectList = lostPropertyProjectRepository.findByComplex(pageSize, offsetNumber);
        for(LostPropertyProject lostPropertyProject : lostPropertyProjectList){
            LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();
            BeanUtils.copyProperties(lostPropertyProject,lostPropertyProjectDTO,"picture");
            if(lostPropertyProject.getPicture()!=null){
                lostPropertyProjectDTO.setPicture(lostPropertyProject.getPicture().split(","));
            }
            result.add(lostPropertyProjectDTO);
        }
        return result;
    }

    /**
     * 更新项目，用于其他项目调用，当其他项目要修改信息时，调用此方法保存修改后的值
     * @param lostPropertyProjectDTO
     * @return
     */
    @Override
    public LostPropertyProjectDTO updateLostPropertyProject(LostPropertyProjectDTO lostPropertyProjectDTO) {
        LostPropertyProject lostPropertyProject = new LostPropertyProject();
        BeanUtils.copyProperties(lostPropertyProjectDTO,lostPropertyProject,"picture");
        String pictureArray = "";
        if(lostPropertyProjectDTO.getPicture()!=null){
            for(String picture : lostPropertyProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            lostPropertyProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("更新失物招领项目："+lostPropertyProject);
        LostPropertyProject result = lostPropertyProjectRepository.save(lostPropertyProject);
        LostPropertyProjectDTO resultDTO = new LostPropertyProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    /**
     * 增加项目浏览量
     * @param projectId 项目Id
     * @return
     */
    @Override
    public LostPropertyProjectDTO increasePageviews(String projectId) {
        LostPropertyProject lostPropertyProject = lostPropertyProjectRepository.findByProjectId(projectId);
        if (lostPropertyProject==null){
            throw new SellException(ResultEnum.PROJECT_ID_NOT_FOUND);
        }
        lostPropertyProject.setPageviews(lostPropertyProject.getPageviews()+1);
        lostPropertyProjectRepository.save(lostPropertyProject);
        LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();
        BeanUtils.copyProperties(lostPropertyProject,lostPropertyProjectDTO,"picture");
        if(lostPropertyProject.getPicture()!=null){
            lostPropertyProjectDTO.setPicture(lostPropertyProject.getPicture().split(","));
        }
        return lostPropertyProjectDTO;
    }

    /**
     * 按照标签查询，并按时间排序（最新）
     * @param keyword 标签关键字
     * @param pageable 分页请求
     * @return
     */
    @Override
    public List<LostPropertyProjectDTO> findLostPropertyProjectByTagsLikeOrderByUpdateTime(String keyword, Pageable pageable) {
        List<LostPropertyProjectDTO> result = new ArrayList<>();
        Page<LostPropertyProject> page = lostPropertyProjectRepository.findByTagsLikeOrderByUpdateTimeDesc("%" + keyword + "%", pageable);
        for(LostPropertyProject lostPropertyProject : page){
            LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();
            BeanUtils.copyProperties(lostPropertyProject,lostPropertyProjectDTO,"picture");
            if(lostPropertyProject.getPicture()!=null){
                lostPropertyProjectDTO.setPicture(lostPropertyProject.getPicture().split(","));
            }
            result.add(lostPropertyProjectDTO);
        }
        return result;
    }

    /**
     * 根据标签关键字查找，按照热度排序
     * @param keyword 标签关键字
     * @param pageable 分页请求
     * @return
     */
    @Override
    public List<LostPropertyProjectDTO> findLostPropertyProjectByTagsLikeOrderByFavoritesNumber(String keyword, Pageable pageable) {
        List<LostPropertyProjectDTO> result = new ArrayList<>();
        Page<LostPropertyProject> page = lostPropertyProjectRepository.findByTagsLikeOrderByFavoriteNumberDesc("%" + keyword + "%", pageable);
        for(LostPropertyProject lostPropertyProject : page){
            LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();
            BeanUtils.copyProperties(lostPropertyProject,lostPropertyProjectDTO,"picture");
            if(lostPropertyProject.getPicture()!=null){
                lostPropertyProjectDTO.setPicture(lostPropertyProject.getPicture().split(","));
            }
            result.add(lostPropertyProjectDTO);
        }
        return result;
    }

    /**
     * 处理标签（向上兼容），不存在则创建
     * @param lostPropertyProjectFrom
     */
    @Override
    public void tagHandler(LostPropertyProjectFrom lostPropertyProjectFrom) {
        TagForm tagForm = new TagForm();
        tagForm.setUserOpenId(lostPropertyProjectFrom.getOpenid());
        tagForm.setTagContent(lostPropertyProjectFrom.getTags());
        tagService.createTag(tagForm);
    }

}
