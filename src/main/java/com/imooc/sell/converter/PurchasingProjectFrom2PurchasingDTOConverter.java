package com.imooc.sell.converter;

//import com.google.gson.Gson;


import com.imooc.sell.controller.form.LostPropertyProjectFrom;
import com.imooc.sell.controller.form.PurchasingProjectFrom;
import com.imooc.sell.dto.LostPropertyProjectDTO;
import com.imooc.sell.dto.PurchasingProjectDTO;

import java.util.Date;

public class PurchasingProjectFrom2PurchasingDTOConverter {
    public static PurchasingProjectDTO convert(PurchasingProjectFrom purchasingProjectFrom) {
        PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();

        purchasingProjectDTO.setUserOpenId(purchasingProjectFrom.getOpenid());
        purchasingProjectDTO.setTitle(purchasingProjectFrom.getTitle());
        purchasingProjectDTO.setAmount(purchasingProjectFrom.getAmount());
        purchasingProjectDTO.setContactNumber(purchasingProjectFrom.getContactNumber());
        purchasingProjectDTO.setContent(purchasingProjectFrom.getContent());
        purchasingProjectDTO.setGender(purchasingProjectFrom.getGender());
        purchasingProjectDTO.setPicture(purchasingProjectFrom.getPicture());
        purchasingProjectDTO.setLocation(purchasingProjectFrom.getLocation());
        purchasingProjectDTO.setFavoriteNumber(0);
        purchasingProjectDTO.setPageviews(0);
        purchasingProjectDTO.setType(1);
        purchasingProjectDTO.setCreateTime(new Date());
        purchasingProjectDTO.setUpdateTime(new Date());
        String tags = "";
        if(purchasingProjectFrom.getTags()!=null){
            for(String tag : purchasingProjectFrom.getTags())tags = tags + tag + ",";
            purchasingProjectDTO.setTags(tags.substring(0,tags.length()-1));
        }
        return purchasingProjectDTO;
    }
}
