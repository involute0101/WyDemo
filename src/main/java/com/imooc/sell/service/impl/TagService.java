package com.imooc.sell.service.impl;

import com.imooc.sell.controller.form.TagForm;
import com.imooc.sell.dataobject.Tag;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserInfoServiceImpl userInfoService;

    /**
     * 根据userOpenId查询tag
     * @param userOpenId 用户openId
     * @return
     */
    public List<Tag> findTagByUserOpenId(String userOpenId){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(userOpenId);
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        return tagRepository.findByUserOpenId(userOpenId);
    }

    /**
     * 创建tag标签
     * @param tagForm 用户输入表单
     * @return
     */
    public List<Tag> createTag(TagForm tagForm){
        List<Tag> tags = new ArrayList<>();
        for(String tagContent : tagForm.getTagContent()){
            Tag tag = new Tag();
            tag.setUserOpenId(tagForm.getUserOpenId());
            tag.setTagContent(tagContent);
            tags.add(tagRepository.save(tag)) ;
        }
        return tags;
    }

    public List<Tag> findByTagContentLike(String keyword, Pageable pageable){
        List<Tag> tags = new ArrayList<>();
        Page<Tag> page = tagRepository.findByTagContentLike("%" + keyword + "%", pageable);
        for(Tag tag : page)tags.add(tag);
        return tags;
    }
}
