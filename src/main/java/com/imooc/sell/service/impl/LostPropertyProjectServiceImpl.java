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
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(lostPropertyProjectDTO.getUserOpenId());
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
        logger.info("???????????????????????????"+lostPropertyProject.toString());
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
     * ?????????????????????????????????????????????
     * @param titleKeyword  ??????????????????
     * @param pageable  ????????????
     * @return
     */
    @Override
    public List<LostPropertyProjectDTO> findLostPropertyProjectByTitleLike(String titleKeyword, Pageable pageable) {
        List<LostPropertyProjectDTO> list = new ArrayList<>();
        Page<LostPropertyProject> page = lostPropertyProjectRepository.findByTitleLike("%" + titleKeyword + "%", pageable);
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
     * ?????????????????????????????????????????????
     * @param pageable ????????????
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
     * ???????????????????????????????????????????????????
     * @param pageable
     * @param sort ?????????asc????????????desc?????????
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
     * ???????????????????????????=?????????*0.000001+?????????*10
     * @param pageable  ????????????
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
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
        logger.info("???????????????????????????"+lostPropertyProject);
        LostPropertyProject result = lostPropertyProjectRepository.save(lostPropertyProject);
        LostPropertyProjectDTO resultDTO = new LostPropertyProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    /**
     * ?????????????????????
     * @param projectId ??????Id
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
     * ???????????????????????????????????????????????????
     * @param keyword ???????????????
     * @param pageable ????????????
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
     * ????????????????????????????????????????????????
     * @param keyword ???????????????
     * @param pageable ????????????
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
     * ???????????????????????????????????????????????????
     * @param lostPropertyProjectFrom
     */
    @Override
    public void tagHandler(LostPropertyProjectFrom lostPropertyProjectFrom) {
        TagForm tagForm = new TagForm();
        tagForm.setUserOpenId(lostPropertyProjectFrom.getOpenid());
        tagForm.setTagContent(lostPropertyProjectFrom.getTags());
        tagService.createTag(tagForm,1);
    }

}
