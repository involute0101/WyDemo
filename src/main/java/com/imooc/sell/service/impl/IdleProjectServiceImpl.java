package com.imooc.sell.service.impl;

import com.imooc.sell.controller.form.IdleProjectFrom;
import com.imooc.sell.controller.form.TagForm;
import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.IdleProjectRepository;
import com.imooc.sell.service.IdleProjectService;
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
public class IdleProjectServiceImpl implements IdleProjectService {

    public static final Logger logger = LoggerFactory.getLogger(IdleProjectServiceImpl.class);

    @Autowired
    IdleProjectRepository idleProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Autowired
    TagService tagService;

    @Override
    @Transactional
    public IdleProjectDTO createIdleProjectOne(IdleProjectDTO idleProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(idleProjectDTO.getUserOpenId());
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(4);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        idleProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        IdleProject idleProject = new IdleProject();
        BeanUtils.copyProperties(idleProjectDTO,idleProject,"picture");
        String pictureArray = "";
        if(idleProjectDTO.getPicture()!=null) {
            for(String picture : idleProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            idleProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("?????????????????????"+idleProject.toString());
        IdleProject result = idleProjectRepository.save(idleProject);
        IdleProjectDTO resultDTO = new IdleProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    @Override
    public List<IdleProjectDTO> findIdleProjectsOrderByUpdateTime(Pageable pageable) {
        Page<IdleProject> page = idleProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<IdleProjectDTO> list = new ArrayList<>();

        for (IdleProject idleProject: page){
            IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
            BeanUtils.copyProperties(idleProject, idleProjectDTO,"picture");
            if (idleProject.getPicture()!=null){
                idleProjectDTO.setPicture(idleProject.getPicture().split(","));
            }
            list.add(idleProjectDTO);
        }
        return list;
    }


    @Override
    public IdleProjectDTO findIdleProjectByProjectId(String projectId) {
        IdleProject idleProject = idleProjectRepository.findByProjectId(projectId);
        IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
        if(idleProject != null) {
            BeanUtils.copyProperties(idleProject, idleProjectDTO,"picture");
            if (idleProject.getPicture()!=null){
                idleProjectDTO.setPicture(idleProject.getPicture().split(","));
            }
            return idleProjectDTO;
        }
        else {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }
    }

    @Override
    public List<IdleProjectDTO> findIdleProjectByTagsLike(String keyword, Pageable pageable) {
        List<IdleProjectDTO> list = new ArrayList<>();
        Page<IdleProject> page = idleProjectRepository.findByTagsLike("%" + keyword + "%", pageable);
        for(IdleProject idleProject:page){
            IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
            BeanUtils.copyProperties(idleProject,idleProjectDTO,"picture");
            if (idleProject.getPicture()!=null){
                idleProjectDTO.setPicture(idleProject.getPicture().split(","));
            }
            list.add(idleProjectDTO);
        }
        return list;
    }

    /**
     * ???????????????????????????????????????????????????
     * @param pageable ????????????
     * @param sort ????????????
     * @return
     */
    @Override
    public List<IdleProjectDTO> findIdleProjectOrderByAmount(Pageable pageable, String sort) {
        Page<IdleProject> page = null;
        if ("desc".equals(sort))page = idleProjectRepository.findByOrderByAmountDesc(pageable);
        else page = idleProjectRepository.findByOrderByAmount(pageable);
        List<IdleProjectDTO> arrayList = new ArrayList<>();
        for(IdleProject idleProject : page){
            IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
            BeanUtils.copyProperties(idleProject,idleProjectDTO,"picture");
            if (idleProject.getPicture()!=null){
                idleProjectDTO.setPicture(idleProject.getPicture().split(","));
            }
            arrayList.add(idleProjectDTO);
        }
        return arrayList;
    }

    /**
     * ???????????????????????????????????????
     * @param titleKeyword  ???????????????
     * @param pageable ????????????
     * @return
     */
    @Override
    public List<IdleProjectDTO> findIdleProjectByTitleLike(String titleKeyword,Pageable pageable){
        List<IdleProjectDTO> list = new ArrayList<>();
        Page<IdleProject> page = idleProjectRepository.findByTitleLike("%" + titleKeyword + "%", pageable);
        for(IdleProject idleProject : page){
            IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
            BeanUtils.copyProperties(idleProject,idleProjectDTO,"picture");
            if (idleProject.getPicture()!=null){
                idleProjectDTO.setPicture(idleProject.getPicture().split(","));
            }
            list.add(idleProjectDTO);
        }
        return list;
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @param idleProjectDTO
     * @return
     */
    @Override
    public IdleProjectDTO updateIdleProjectDTO(IdleProjectDTO idleProjectDTO) {
        IdleProject idleProject = new IdleProject();
        BeanUtils.copyProperties(idleProjectDTO,idleProject,"picture");
        String pictureArray = "";
        if(idleProjectDTO.getPicture()!=null) {
            for(String picture : idleProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            idleProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("?????????????????????"+idleProject.toString());
        IdleProject result = idleProjectRepository.save(idleProject);
        IdleProjectDTO resultDTO = new IdleProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    /**
     * ?????????????????????????????????????????????
     * @param pageable ????????????
     * @return
     */
    @Override
    public List<IdleProjectDTO> findIdleProjectOrderByFavoritesNumber(Pageable pageable) {
        Page<IdleProject> page = idleProjectRepository.findByOrderByFavoriteNumberDesc(pageable);
        List<IdleProjectDTO> list = new ArrayList<>();
        for(IdleProject idleProject : page){
            IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
            BeanUtils.copyProperties(idleProject,idleProjectDTO,"picture");
            if (idleProject.getPicture()!=null){
                idleProjectDTO.setPicture(idleProject.getPicture().split(","));
            }
            list.add(idleProjectDTO);
        }
        return list;
    }

    /**
     * ?????????????????????
     * @param projectId ??????Id
     * @return
     */
    @Override
    public IdleProjectDTO increasePageviews(String projectId) {
        IdleProject idleProject = idleProjectRepository.findByProjectId(projectId);
        if(idleProject==null){
            throw new SellException(ResultEnum.PROJECT_ID_NOT_FOUND);
        }
        idleProject.setPageviews(idleProject.getPageviews()+1);
        idleProjectRepository.save(idleProject);
        IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
        BeanUtils.copyProperties(idleProject,idleProjectDTO,"picture");
        if (idleProject.getPicture()!=null){
            idleProjectDTO.setPicture(idleProject.getPicture().split(","));
        }
        return idleProjectDTO;
    }

    /**
     * ???????????????????????????=?????????*0.000001+?????????*10
     * @param pageable  ????????????
     * @return
     */
    @Override
    public List<IdleProjectDTO> findByComplexService(Pageable pageable) {
        List<IdleProjectDTO> result = new ArrayList<>();
        int pageSize = pageable.getPageSize();
        int offsetNumber = pageable.getOffset();
        List<IdleProject> idleProjectList = idleProjectRepository.findByComplex(pageSize, offsetNumber);
        for(IdleProject idleProject : idleProjectList){
            IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
            BeanUtils.copyProperties(idleProject,idleProjectDTO,"picture");
            if (idleProject.getPicture()!=null){
                idleProjectDTO.setPicture(idleProject.getPicture().split(","));
            }
            result.add(idleProjectDTO);
        }
        return result;
    }

    /**
     * ???????????????????????????????????????????????????
     * @param keyword ???????????????
     * @param pageable ????????????
     * @return
     */
    @Override
    public List<IdleProjectDTO> findIdleProjectByTagsLikeOrderByUpdateTime(String keyword, Pageable pageable) {
        List<IdleProjectDTO> result = new ArrayList<>();
        Page<IdleProject> page = idleProjectRepository.findByTagsLikeOrderByUpdateTimeDesc("%" + keyword + "%", pageable);
        for(IdleProject idleProject : page){
            IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
            BeanUtils.copyProperties(idleProject,idleProjectDTO,"picture");
            if (idleProject.getPicture()!=null){
                idleProjectDTO.setPicture(idleProject.getPicture().split(","));
            }
            result.add(idleProjectDTO);
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
    public List<IdleProjectDTO> findIdleProjectByTagsLikeOrderByFavoritesNumber(String keyword, Pageable pageable) {
        List<IdleProjectDTO> result = new ArrayList<>();
        Page<IdleProject> page = idleProjectRepository.findByTagsLikeOrderByFavoriteNumberDesc("%" + keyword + "%", pageable);
        for(IdleProject idleProject : page){
            IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
            BeanUtils.copyProperties(idleProject,idleProjectDTO,"picture");
            if (idleProject.getPicture()!=null){
                idleProjectDTO.setPicture(idleProject.getPicture().split(","));
            }
            result.add(idleProjectDTO);
        }
        return result;
    }

    /**
     * ???????????????????????????????????????????????????
     * @param idleProjectFrom
     */
    @Override
    public void tagHandler(IdleProjectFrom idleProjectFrom) {
        TagForm tagForm = new TagForm();
        tagForm.setUserOpenId(idleProjectFrom.getOpenid());
        tagForm.setTagContent(idleProjectFrom.getTags());
        tagService.createTag(tagForm,1);
    }
}
