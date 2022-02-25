package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.Favorites;
import com.imooc.sell.dto.*;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.FavoritesRepository;
import com.imooc.sell.service.FavoritesService;
import com.imooc.sell.service.UserInfoService;
import com.sun.org.apache.bcel.internal.generic.FADD;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class FavoritesServiceServiceImpl implements FavoritesService {

    public static final Logger logger = LoggerFactory.getLogger(FavoritesServiceServiceImpl.class);

    @Autowired
    FavoritesRepository favoritesRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Autowired
    PurchasingServiceImpl purchasingService;

    @Autowired
    RewardServiceImpl rewardService;

    @Autowired
    StudyServiceImpl studyService;

    @Autowired
    IdleProjectServiceImpl idleProjectService;

    @Autowired
    LostPropertyProjectServiceImpl lostPropertyProjectService;

    @Autowired
    JobsProjectServiceImpl jobsProjectService;


    @Override
    @Transactional
    public FavoritesDTO createFavoriteOne(FavoritesDTO favoritesDTO) {
        String userOpenId = favoritesDTO.getUserOpenId();
        String projectId = favoritesDTO.getProjectId();
        //验证用户ID是否存在
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(userOpenId);
        if (userInfoDTO == null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        //验证项目ID是否存在
        ProjectMasterDTO projectMasterDTO = projectMasterService.findProjectMasterByProjectId(projectId);
        if (projectMasterDTO == null){
            throw  new SellException(ResultEnum.PROJECT_ID_NOT_FOUND);
        }
        //判断是否已存在收藏记录
        Favorites favorites = favoritesRepository.findByUserIdAndProjectId(userInfoDTO.getUserId(),projectId);
        if (favorites != null){
            throw new SellException(ResultEnum.FAVORITES_EXISTED);
        }
        Integer projectType = projectMasterDTO.getProjectType();
        switch (projectType){
            case 1:
                PurchasingProjectDTO purchasingProjectDTO = purchasingService.findPurchasingByProjectId(projectId);
                purchasingProjectDTO.setFavoriteNumber(purchasingProjectDTO.getFavoriteNumber()+1);
                purchasingService.updatePurchasingProject(purchasingProjectDTO);
                break;
            case 2:
                RewardProjectDTO rewardProjectDTO = rewardService.findRewardByProjectId(projectId);
                System.out.println(rewardProjectDTO);
                rewardProjectDTO.setFavoriteNumber(rewardProjectDTO.getFavoriteNumber()+1);
                rewardService.updateRewardProject(rewardProjectDTO);
                break;
            case 3:
                StudyProjectDTO studyProjectDTO = studyService.findStudyByProjectId(projectId);
                studyProjectDTO.setFavoriteNumber(studyProjectDTO.getFavoriteNumber()+1);
                studyService.updateStudyProjectDTO(studyProjectDTO);
                break;
            case 4:
                IdleProjectDTO idleProjectDTO = idleProjectService.findIdleProjectByProjectId(projectId);
                idleProjectDTO.setFavoriteNumber(idleProjectDTO.getFavoriteNumber()+1);
                idleProjectService.updateIdleProjectDTO(idleProjectDTO);
                break;
            case 5:
                LostPropertyProjectDTO lostPropertyProjectDTO = lostPropertyProjectService.findLostPropertyProjectByProjectId(projectId);
                lostPropertyProjectDTO.setFavoriteNumber(lostPropertyProjectDTO.getFavoriteNumber()+1);
                lostPropertyProjectService.updateLostPropertyProject(lostPropertyProjectDTO);
                break;
            case 6:
                JobsProjectDTO jobsProjectDTO = jobsProjectService.findJobsProjectByProjectId(projectId);
                jobsProjectDTO.setFavoriteNumber(jobsProjectDTO.getFavoriteNumber()+1);
                jobsProjectService.updateJobsProject(jobsProjectDTO);
                break;
        }
        //写表
        favorites = new Favorites();
        favorites.setUserId(userInfoDTO.getUserId());
        BeanUtils.copyProperties(favoritesDTO,favorites);
        logger.info("创建收藏项目:"+favorites);
        favoritesRepository.save(favorites);
        FavoritesDTO favoritesDTOResult = new FavoritesDTO();
        BeanUtils.copyProperties(favorites, favoritesDTOResult);
        favoritesDTOResult.setUserOpenId(userOpenId);
        return favoritesDTOResult;
    }

    @Override
    @Transactional
    public boolean deleteFavoriteOne(String userOpenId, String projectId) {
        //判断是否存在该用户
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(userOpenId);
        if (userInfoDTO == null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        //判断是否存在收藏记录
        Favorites favorites = favoritesRepository.findByUserIdAndProjectId(userInfoDTO.getUserId(),projectId);
        if (favorites == null){
            throw new SellException(ResultEnum.FAVORITES_NOT_EXISTED);
        }
        //写表
        if(favoritesRepository.deleteByProjectIdAndUserId(projectId,userInfoDTO.getUserId()) != 0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public List<UserInfoDTO> findAllFavoriteUser(String projectId) {
        List<Favorites> favorites = favoritesRepository.findByProjectId(projectId);
        Collection<Integer> collection = new ArrayList<>();
        for(Favorites favorites1: favorites){
            collection.add(favorites1.getUserId());
        }
        List<UserInfoDTO> userInfoDTOS = userInfoService.findUserInfos(collection);
        return userInfoDTOS;
    }

    @Override
    public Integer findNumOfFavoriteUser(String projectId) {
        int num = -1;
        num = favoritesRepository.countByProjectId(projectId);
        return num;
    }

    @Override
    public List<ProjectMasterDTO> findAllFavoriteProject(Integer userId) {
        List<Favorites> list = favoritesRepository.findByUserId(userId);
        List<ProjectMasterDTO> projectMasterDTOS = new ArrayList<>();
        for (Favorites favorites: list){
            ProjectMasterDTO projectMasterDTO = projectMasterService.findProjectMasterByProjectId(favorites.getProjectId());
            projectMasterDTOS.add(projectMasterDTO);
        }
        return projectMasterDTOS;
    }

    @Override
    public Integer findNumOfFavoriteProject(Integer userId) {
        int num = -1;
        num = favoritesRepository.countByUserId(userId);
        return num;
    }

    @Override
    public FavoritesDTO findFavoriteOne(String userOpenId, String projectId) {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(userOpenId);
        Favorites favorites = favoritesRepository.findByProjectIdAndUserId(projectId, userInfoDTO.getUserId());
        FavoritesDTO favoritesDTO = new FavoritesDTO();
        if (favorites != null){
            BeanUtils.copyProperties(favorites,favoritesDTO);
            return favoritesDTO;
        }
        else
            return null;
    }

    /**
     * 检查项目是否被某一用户收藏
     * @param projectId 项目id
     * @param userId 用户id
     * @return
     */
    public boolean checkProjectFavoritedByUser(String projectId,String userId){
        Favorites favorites = favoritesRepository.findByProjectIdAndUserId(projectId, Integer.valueOf(userId));
        if(favorites != null)return true;
        return false;
    }
}
