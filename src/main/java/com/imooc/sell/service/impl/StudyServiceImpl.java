package com.imooc.sell.service.impl;

import com.imooc.sell.controller.form.StudyProjectFrom;
import com.imooc.sell.controller.form.TagForm;
import com.imooc.sell.dataobject.PurchasingProject;
import com.imooc.sell.dataobject.StudyProject;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.PurchasingProjectDTO;
import com.imooc.sell.dto.StudyProjectDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.StudyProjectRepository;
import com.imooc.sell.service.StudyProjectService;
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
public class StudyServiceImpl implements StudyProjectService {

    public static final Logger logger = LoggerFactory.getLogger(StudyServiceImpl.class);

    @Autowired
    StudyProjectRepository studyProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Autowired
    TagService tagService;

    @Override
    @Transactional
    public StudyProjectDTO createStudyProject(StudyProjectDTO studyProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(studyProjectDTO.getUserOpenid());
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(3);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        studyProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        StudyProject studyProject = new StudyProject();
        BeanUtils.copyProperties(studyProjectDTO,studyProject,"picture");
        String pictureArray = "";
        if(studyProjectDTO.getPicture()!=null){
            for(String picture : studyProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            studyProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("创建学习项目:"+studyProject.toString());
        StudyProject result = studyProjectRepository.save(studyProject);
        StudyProjectDTO resultDTO = new StudyProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    @Override
    public List<StudyProjectDTO> findStudyProjectsOrderByUpdateTime(Pageable pageable) {
        Page<StudyProject> page = studyProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<StudyProjectDTO> list = new ArrayList<>();

        for (StudyProject studyProject: page){
            StudyProjectDTO studyProjectDTO = new StudyProjectDTO();
            BeanUtils.copyProperties(studyProject, studyProjectDTO,"picture");
            if (studyProject.getPicture()!=null){
                studyProjectDTO.setPicture(studyProject.getPicture().split(","));
            }
            list.add(studyProjectDTO);
        }
        return list;
    }

    /**
     * 根据收藏数降序，查询最热的项目
     * @param pageable 分页请求
     * @return
     */
    @Override
    public List<StudyProjectDTO> findStudyProjectsOrderByFavoritesNumber(Pageable pageable) {
        Page<StudyProject> page = studyProjectRepository.findByOrderByFavoriteNumberDesc(pageable);
        List<StudyProjectDTO> list = new ArrayList<>();
        for (StudyProject studyProject: page){
            StudyProjectDTO studyProjectDTO = new StudyProjectDTO();
            BeanUtils.copyProperties(studyProject, studyProjectDTO,"picture");
            if (studyProject.getPicture()!=null){
                studyProjectDTO.setPicture(studyProject.getPicture().split(","));
            }
            list.add(studyProjectDTO);
        }
        return list;
    }

    @Override
    public StudyProjectDTO findStudyByProjectId(String projectId) {
        StudyProject studyProject = studyProjectRepository.findByProjectId(projectId);
        StudyProjectDTO studyProjectDTO = new StudyProjectDTO();
        if(studyProject != null) {
            BeanUtils.copyProperties(studyProject, studyProjectDTO,"picture");
            if (studyProject.getPicture()!=null){
                studyProjectDTO.setPicture(studyProject.getPicture().split(","));
            }
            return studyProjectDTO;
        }
        else {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }

    }

    @Override
    public List<StudyProjectDTO> findStudyProjectByTagsLike(String keyword, Pageable pageable) {
        List<StudyProjectDTO> list = new ArrayList<>();
        Page<StudyProject> page = studyProjectRepository.findByTagsLike("%" + keyword + "%", pageable);
        for(StudyProject studyProject: page){
            StudyProjectDTO studyProjectDTO = new StudyProjectDTO();
            BeanUtils.copyProperties(studyProject,studyProjectDTO,"picture");
            if (studyProject.getPicture()!=null){
                studyProjectDTO.setPicture(studyProject.getPicture().split(","));
            }
            list.add(studyProjectDTO);
        }
        return list;
    }

    /**
     * 更新项目，用于其他项目调用，当其他项目要修改信息时，调用此方法保存修改后的值
     * @param studyProjectDTO
     * @return
     */
    @Override
    public StudyProjectDTO updateStudyProjectDTO(StudyProjectDTO studyProjectDTO) {
        StudyProject studyProject = new StudyProject();
        BeanUtils.copyProperties(studyProjectDTO,studyProject,"picture");
        String pictureArray = "";
        for(String picture : studyProjectDTO.getPicture()){
            pictureArray = pictureArray + picture + ",";
        }
        studyProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        logger.info("更新学习项目:"+studyProject);
        StudyProject result = studyProjectRepository.save(studyProject);
        StudyProjectDTO resultDTO = new StudyProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    /**
     * 增加项目浏览量
     * @param projectId 项目Id
     * @return
     */
    public StudyProjectDTO increasePageviews(String projectId) {
        StudyProject studyProject = studyProjectRepository.findByProjectId(projectId);
        if(studyProject==null){
            throw new SellException(ResultEnum.PROJECT_ID_NOT_FOUND);
        }
        studyProject.setPageviews(studyProject.getPageviews()+1);
        studyProjectRepository.save(studyProject);
        StudyProjectDTO studyProjectDTO = new StudyProjectDTO();
        BeanUtils.copyProperties(studyProject,studyProjectDTO,"picture");
        if (studyProject.getPicture()!=null){
            studyProjectDTO.setPicture(studyProject.getPicture().split(","));
        }
        return studyProjectDTO;
    }

    /**
     * 按照标签查询，并按时间排序（最新）
     * @param keyword 标签关键字
     * @param pageable 分页请求
     * @return
     */
    @Override
    public List<StudyProjectDTO> findStudyProjectByTagsLikeOrderByUpdateTime(String keyword, Pageable pageable) {
        List<StudyProjectDTO> result = new ArrayList<>();
        Page<StudyProject> page = studyProjectRepository.findByTagsLikeOrderByUpdateTimeDesc("%" + keyword + "%", pageable);
        for(StudyProject studyProject : page){
            StudyProjectDTO studyProjectDTO = new StudyProjectDTO();
            BeanUtils.copyProperties(studyProject,studyProjectDTO,"picture");
            if (studyProject.getPicture()!=null){
                studyProjectDTO.setPicture(studyProject.getPicture().split(","));
            }
            result.add(studyProjectDTO);
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
    public List<StudyProjectDTO> findStudyProjectByTagsLikeOrderByFavoritesNumber(String keyword, Pageable pageable) {
        List<StudyProjectDTO> result = new ArrayList<>();
        Page<StudyProject> page = studyProjectRepository.findByTagsLikeOrderByFavoriteNumberDesc("%" + keyword + "%", pageable);
        for(StudyProject studyProject : page){
            StudyProjectDTO studyProjectDTO = new StudyProjectDTO();
            BeanUtils.copyProperties(studyProject,studyProjectDTO,"picture");
            if (studyProject.getPicture()!=null){
                studyProjectDTO.setPicture(studyProject.getPicture().split(","));
            }
            result.add(studyProjectDTO);
        }
        return result;
    }

    /**
     * 处理标签（向上兼容），不存在则创建
     * @param studyProjectFrom
     */
    @Override
    public void tagHandler(StudyProjectFrom studyProjectFrom) {
        TagForm tagForm = new TagForm();
        tagForm.setUserOpenId(studyProjectFrom.getOpenid());
        tagForm.setTagContent(studyProjectFrom.getTags());
        tagService.createTag(tagForm);
    }
}
