package com.imooc.sell.service.impl;

import com.imooc.sell.controller.form.PurchasingProjectFrom;
import com.imooc.sell.controller.form.TagForm;
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

    @Autowired
    TagService tagService;

    @Override
    @Transactional
    public PurchasingProjectDTO createPurchasingProject(PurchasingProjectDTO purchasingProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(purchasingProjectDTO.getUserOpenId());
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
        logger.info("?????????????????????"+purchasingProject.toString());
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
     * ???????????????????????????????????????????????????
     * @param pageable ????????????
     * @param sort ????????????
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

    /**
     * ???????????????????????????????????????
     * @param titleKeyword ???????????????
     * @param pageable ????????????
     * @return
     */
    @Override
    public List<PurchasingProjectDTO> findByPurchasingProjectByTitleLike(String titleKeyword, Pageable pageable) {
        List<PurchasingProjectDTO> list = new ArrayList<>();
        Page<PurchasingProject> page = purchasingProjectRepository.findByTitleLike("%" + titleKeyword + "%", pageable);
        for(PurchasingProject purchasingProject : page){
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
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @param purchasingProjectDTO
     * @return
     */
    @Override
    public PurchasingProjectDTO updatePurchasingProject(PurchasingProjectDTO purchasingProjectDTO) {
        PurchasingProject purchasingProject = new PurchasingProject();
        BeanUtils.copyProperties(purchasingProjectDTO,purchasingProject,"picture");
        String pictureArray = "";
        if(purchasingProjectDTO.getPicture()!=null){
            for(String picture : purchasingProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            purchasingProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("?????????????????????"+purchasingProject);
        PurchasingProject result = purchasingProjectRepository.save(purchasingProject);
        PurchasingProjectDTO resultDTO = new PurchasingProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    /**
     * ?????????????????????????????????????????????
     * @param pageable ????????????
     * @return
     */
    @Override
    public List<PurchasingProjectDTO> findPurchasingProjectOrderByFavoritesNumber(Pageable pageable) {
        Page<PurchasingProject> page = purchasingProjectRepository.findByOrderByFavoriteNumberDesc(pageable);
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

    /**
     * ???????????????????????????=?????????*0.000001+?????????*10
     * @param pageable ????????????
     * @return
     */
    @Override
    public List<PurchasingProjectDTO> findByComplexService(Pageable pageable) {
        List<PurchasingProjectDTO> result = new ArrayList<>();
        int pageSize = pageable.getPageSize();
        int offsetNumber = pageable.getOffset();
        List<PurchasingProject> purchasingProjectList = purchasingProjectRepository.findByComplex(pageSize, offsetNumber);
        for(PurchasingProject purchasingProject : purchasingProjectList){
            PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();
            BeanUtils.copyProperties(purchasingProject, purchasingProjectDTO,"picture");
            if(purchasingProject.getPicture()!=null){
                purchasingProjectDTO.setPicture(purchasingProject.getPicture().split(","));
            }
            result.add(purchasingProjectDTO);
        }
        return result;
    }

    /**
     * ?????????????????????
     * @param projectId ??????Id
     * @return
     */
    @Override
    public PurchasingProjectDTO increasePageviews(String projectId) {
        PurchasingProject purchasingProject = purchasingProjectRepository.findByProjectId(projectId);
        if(purchasingProject==null){
            throw new SellException(ResultEnum.PROJECT_ID_NOT_FOUND);
        }
        purchasingProject.setPageviews(purchasingProject.getPageviews()+1);
        purchasingProjectRepository.save(purchasingProject);
        PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();
        BeanUtils.copyProperties(purchasingProject, purchasingProjectDTO,"picture");
        if(purchasingProject.getPicture()!=null){
            purchasingProjectDTO.setPicture(purchasingProject.getPicture().split(","));
        }
        return purchasingProjectDTO;
    }

    /**
     * ???????????????????????????????????????????????????
     * @param keyword ???????????????
     * @param pageable ????????????
     * @return
     */
    @Override
    public List<PurchasingProjectDTO> findPurchasingProjectByTagsLikeOrderByUpdateTime(String keyword, Pageable pageable) {
        List<PurchasingProjectDTO> result = new ArrayList<>();
        Page<PurchasingProject> page = purchasingProjectRepository.findByTagsLikeOrderByUpdateTimeDesc("%" + keyword + "%", pageable);
        for(PurchasingProject purchasingProject : page){
            PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();
            BeanUtils.copyProperties(purchasingProject, purchasingProjectDTO,"picture");
            if(purchasingProject.getPicture()!=null){
                purchasingProjectDTO.setPicture(purchasingProject.getPicture().split(","));
            }
            result.add(purchasingProjectDTO);
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
    public List<PurchasingProjectDTO> findPurchasingProjectByTagsLikeOrderByFavoritesNumber(String keyword, Pageable pageable) {
        List<PurchasingProjectDTO> result = new ArrayList<>();
        Page<PurchasingProject> page = purchasingProjectRepository.findByTagsLikeOrderByFavoriteNumberDesc("%" + keyword + "%", pageable);
        for(PurchasingProject purchasingProject : page){
            PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();
            BeanUtils.copyProperties(purchasingProject, purchasingProjectDTO,"picture");
            if(purchasingProject.getPicture()!=null){
                purchasingProjectDTO.setPicture(purchasingProject.getPicture().split(","));
            }
            result.add(purchasingProjectDTO);
        }
        return result;
    }

    /**
     * ???????????????????????????????????????????????????
     * @param purchasingProjectFrom
     */
    @Override
    public void tagHandler(PurchasingProjectFrom purchasingProjectFrom) {
        TagForm tagForm = new TagForm();
        tagForm.setUserOpenId(purchasingProjectFrom.getOpenid());
        tagForm.setTagContent(purchasingProjectFrom.getTags());
        tagService.createTag(tagForm,1);
    }
}
