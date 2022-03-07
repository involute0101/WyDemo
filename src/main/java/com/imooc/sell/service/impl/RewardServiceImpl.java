package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.RewardProject;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.RewardProjectDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.RewardProjectRepository;
import com.imooc.sell.service.RewardProjectService;
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
public class RewardServiceImpl implements RewardProjectService {

    public static final Logger logger = LoggerFactory.getLogger(JobsProjectServiceImpl.class);

    @Autowired
    RewardProjectRepository rewardProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Override
    @Transactional
    public RewardProjectDTO createRewardProject(RewardProjectDTO rewardProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(rewardProjectDTO.getUserOpenid());
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(2);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        rewardProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        RewardProject rewardProject = new RewardProject();
        BeanUtils.copyProperties(rewardProjectDTO,rewardProject,"picture");
        String pictureArray = "";
        if(rewardProjectDTO.getPicture()!=null){
            for(String picture : rewardProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            rewardProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("创建悬赏项目:"+rewardProject.toString());
        RewardProject result = rewardProjectRepository.save(rewardProject);
        RewardProjectDTO resultDTO = new RewardProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }



    @Override
    public List<RewardProjectDTO> findRewardProjectsOrderByUpdateTime(Pageable pageable) {
        Page<RewardProject> page = rewardProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<RewardProjectDTO> list = new ArrayList<>();

        for (RewardProject rewardProject: page){
            RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
            BeanUtils.copyProperties(rewardProject, rewardProjectDTO,"picture");
            if(rewardProject.getPicture()!=null){
                rewardProjectDTO.setPicture(rewardProject.getPicture().split(","));
            }
            list.add(rewardProjectDTO);
        }
        return list;
    }

    @Override
    public RewardProjectDTO findRewardByProjectId(String projectId) {
        RewardProject rewardProject = rewardProjectRepository.findByProjectId(projectId);
        RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
        if(rewardProject != null) {
            BeanUtils.copyProperties(rewardProject, rewardProjectDTO,"picture");
            if(rewardProject.getPicture()!=null){
                rewardProjectDTO.setPicture(rewardProject.getPicture().split(","));
            }
            return rewardProjectDTO;
        }
        else {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }

    }

    /**
     * 根据悬赏金额大小排序，查询悬赏项目
     * @param pageable
     * @param sort 升序（asc）降序（desc）方式
     * @return
     */
    @Override
    public List<RewardProjectDTO> findRewardProjectOrderByAmount(Pageable pageable,String sort) {
        Page<RewardProject> page = null;
        if("desc".equals(sort))page = rewardProjectRepository.findByOrderByAmountDesc(pageable);
        else page = rewardProjectRepository.findByOrderByAmount(pageable);
        List<RewardProjectDTO> list = new ArrayList<>();
        for(RewardProject rewardProject : page){
            RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
            BeanUtils.copyProperties(rewardProject, rewardProjectDTO,"picture");
            if(rewardProject.getPicture()!=null){
                rewardProjectDTO.setPicture(rewardProject.getPicture().split(","));
            }
            list.add(rewardProjectDTO);
        }
        return list;
    }

    @Override
    public List<RewardProjectDTO> findRewardProjectLikeTags(String keyword,Pageable pageable) {
        List<RewardProjectDTO> rewardProjectDTOList = new ArrayList<>();
        Page<RewardProject> page = rewardProjectRepository.findByTagsLike("%"+keyword+"%", pageable);
        for(RewardProject rewardProject : page){
            RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
            BeanUtils.copyProperties(rewardProject,rewardProjectDTO,"picture");
            if(rewardProject.getPicture()!=null){
                rewardProjectDTO.setPicture(rewardProject.getPicture().split(","));
            }
            rewardProjectDTOList.add(rewardProjectDTO);
        }
        return rewardProjectDTOList;
    }

    /**
     * 根据projectId查找userOpenId
     * @param projectId 项目id
     * @return 用户OpenId
     */
    public String findUserOpenIdByProjectId(String projectId){
        ProjectMasterDTO projectMaster = projectMasterService.findProjectMasterByProjectId(projectId);
        Integer userId = projectMaster.getUserId();
        UserInfoDTO userInfoDTO = userInfoService.findByUserId(userId);
        return userInfoDTO.getUserOpenid();
    }

    /**
     * 根据标题关键字查找悬赏项目
     * @param titleKeyword  标题的关键字
     * @param pageable  分页请求
     * @return
     */
    @Override
    public List<RewardProjectDTO> findRewardProjectByTitleLike(String titleKeyword,Pageable pageable){
        List<RewardProjectDTO> list = new ArrayList<>();
        Page<RewardProject> page = rewardProjectRepository.findByTitleLike("%" + titleKeyword + "%", pageable);
        for(RewardProject rewardProject : page){
            RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
            BeanUtils.copyProperties(rewardProject,rewardProjectDTO,"picture");
            if(rewardProject.getPicture()!=null){
                rewardProjectDTO.setPicture(rewardProject.getPicture().split(","));
            }
            list.add(rewardProjectDTO);
        }
        return list;
    }

    /**
     * 更新项目，用于其他项目调用，当其他项目要修改信息时，调用此方法保存修改后的值
     * @param rewardProjectDTO
     * @return
     */
    @Override
    public RewardProjectDTO updateRewardProject(RewardProjectDTO rewardProjectDTO) {
        RewardProject rewardProject = new RewardProject();
        BeanUtils.copyProperties(rewardProjectDTO,rewardProject,"picture");
        String pictureArray = "";
        if(rewardProjectDTO.getPicture()!=null){
            for(String picture : rewardProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            rewardProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("更新悬赏项目:"+rewardProject);
        RewardProject result = rewardProjectRepository.save(rewardProject);
        RewardProjectDTO resultDTO = new RewardProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    /**
     * 根据收藏数降序，查询最热的项目
     * @param pageable  分页查询
     * @return
     */
    @Override
    public List<RewardProjectDTO> findRewardProjectOrderByFavoritesNumber(Pageable pageable) {
        Page<RewardProject> page = rewardProjectRepository.findByOrderByFavoriteNumberDesc(pageable);
        List<RewardProjectDTO> list = new ArrayList<>();

        for (RewardProject rewardProject: page){
            RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
            BeanUtils.copyProperties(rewardProject, rewardProjectDTO,"picture");
            if(rewardProject.getPicture()!=null){
                rewardProjectDTO.setPicture(rewardProject.getPicture().split(","));
            }
            list.add(rewardProjectDTO);
        }
        return list;
    }

    /**
     * 综合排序查找，权重=时间戳*0.000001+点赞数*10
     * @param pageable  分页请求
     * @return
     */
    @Override
    public List<RewardProjectDTO> findByComplexService(Pageable pageable) {
        List<RewardProjectDTO> result = new ArrayList<>();
        int pageSize = pageable.getPageSize();
        int offsetNumber = pageable.getOffset();
        List<RewardProject> rewardProjectList = rewardProjectRepository.findByComplex(pageSize, offsetNumber);
        for(RewardProject rewardProject:rewardProjectList){
            RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
            BeanUtils.copyProperties(rewardProject, rewardProjectDTO,"picture");
            if(rewardProject.getPicture()!=null){
                rewardProjectDTO.setPicture(rewardProject.getPicture().split(","));
            }
            result.add(rewardProjectDTO);
        }
        return result;
    }

    /**
     * 增加项目浏览量
     * @param projectId 项目Id
     * @return
     */
    @Override
    public RewardProjectDTO increasePageviews(String projectId) {
        RewardProject rewardProjectByProId = rewardProjectRepository.findByProjectId(projectId);
        if(rewardProjectByProId==null){
            throw new SellException(ResultEnum.PROJECT_ID_NOT_FOUND);
        }
        rewardProjectByProId.setPageviews(rewardProjectByProId.getPageviews()+1);
        rewardProjectRepository.save(rewardProjectByProId);
        RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
        BeanUtils.copyProperties(rewardProjectByProId, rewardProjectDTO,"picture");
        if(rewardProjectByProId.getPicture()!=null){
            rewardProjectDTO.setPicture(rewardProjectByProId.getPicture().split(","));
        }
        return rewardProjectDTO;
    }
}
