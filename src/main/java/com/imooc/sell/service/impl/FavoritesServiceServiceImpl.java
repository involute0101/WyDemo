package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.Favorites;
import com.imooc.sell.dto.FavoritesDTO;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.FavoritesRepository;
import com.imooc.sell.service.FavoritesService;
import com.imooc.sell.service.UserInfoService;
import com.sun.org.apache.bcel.internal.generic.FADD;
import lombok.extern.slf4j.Slf4j;
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
    @Autowired
    FavoritesRepository favoritesRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;


    @Override
    public FavoritesDTO createFavoriteOne(String userOpenId, String projectId) {
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
        //写表
        favorites = new Favorites();
        favorites.setUserId(userInfoDTO.getUserId());
        favorites.setProjectId(projectId);
        favoritesRepository.save(favorites);
        FavoritesDTO favoritesDTO = new FavoritesDTO();
        BeanUtils.copyProperties(favorites, favoritesDTO);
        return favoritesDTO;
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
}
