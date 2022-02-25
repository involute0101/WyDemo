package com.imooc.sell.service;

import com.imooc.sell.dto.FavoritesDTO;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.UserInfoDTO;

import java.util.List;

public interface FavoritesService {
    FavoritesDTO createFavoriteOne (FavoritesDTO favoritesDTO);

    boolean deleteFavoriteOne(String userOpenId, String projectId);

    List<UserInfoDTO> findAllFavoriteUser(String projectId);

    Integer findNumOfFavoriteUser(String projectId);

    List<ProjectMasterDTO> findAllFavoriteProject(Integer userId);

    Integer findNumOfFavoriteProject(Integer userId);

    FavoritesDTO findFavoriteOne(String userOpenId, String projectId);

}
