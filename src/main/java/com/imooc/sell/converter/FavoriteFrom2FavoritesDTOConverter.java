package com.imooc.sell.converter;

import com.imooc.sell.controller.form.FavoritesForm;
import com.imooc.sell.controller.form.IdleProjectFrom;
import com.imooc.sell.dto.FavoritesDTO;
import com.imooc.sell.dto.IdleProjectDTO;

import java.util.Date;

public class FavoriteFrom2FavoritesDTOConverter {
    public static FavoritesDTO convert(FavoritesForm favoritesForm){
        FavoritesDTO favoritesDTO = new FavoritesDTO();
        favoritesDTO.setProjectId(favoritesForm.getProjectId());
        favoritesDTO.setUserOpenId(favoritesForm.getUserOpenId());
        favoritesDTO.setCreateTime(new Date());
        favoritesDTO.setUpdateTime(new Date());
        return favoritesDTO;
    }
}
