package com.imooc.sell.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.controller.form.IdleProjectFrom;
import com.imooc.sell.controller.form.MakeFriendProjectForm;
import com.imooc.sell.controller.form.TagForm;
import com.imooc.sell.dataobject.MakeFriendProject;
import com.imooc.sell.dataobject.RewardProject;
import com.imooc.sell.dto.MakeFriendProjectDTO;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.RewardProjectDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.MakeFriendProjectRepository;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MakeFriendProjectServiceImpl {
    public static final Logger logger = LoggerFactory.getLogger(MakeFriendProjectServiceImpl.class);

    @Autowired
    MakeFriendProjectRepository makeFriendProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Autowired
    TagService tagService;

    @Autowired
    LeaveMessageServiceImpl leaveMessageService;

    /**
     * 创建交友项目
     * @param makeFriendProjectDTO
     * @return
     * @throws Exception
     */
    @Transactional
    public MakeFriendProjectDTO createMakeFriendProject(MakeFriendProjectDTO makeFriendProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(makeFriendProjectDTO.getUserOpenId());
        if(userInfoDTO==null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(7);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        makeFriendProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        MakeFriendProject makeFriendProject = new MakeFriendProject();
        BeanUtils.copyProperties(makeFriendProjectDTO,makeFriendProject,"picture");
        String pictureArray = "";
        if(makeFriendProjectDTO.getPicture()!=null){
            for(String picture : makeFriendProjectDTO.getPicture()){
                pictureArray = pictureArray + picture + ",";
            }
            makeFriendProject.setPicture(pictureArray.substring(0,pictureArray.length()-1));
        }
        logger.info("创建悬赏项目:"+makeFriendProject.toString());
        MakeFriendProject save = makeFriendProjectRepository.save(makeFriendProject);
        MakeFriendProjectDTO result = new MakeFriendProjectDTO();
        BeanUtils.copyProperties(save, result,"picture");
        if(!StringUtils.isEmpty(makeFriendProject.getPicture())){
            result.setPicture(makeFriendProject.getPicture().split(","));
        }
        return result;
    }

    /**
     * 处理标签（向上兼容），不存在则创建
     * @param makeFriendProjectForm
     */
    public void tagHandler(MakeFriendProjectForm makeFriendProjectForm) {
        TagForm tagForm = new TagForm();
        tagForm.setUserOpenId(makeFriendProjectForm.getOpenid());
        tagForm.setTagContent(makeFriendProjectForm.getTags());
        tagService.createTag(tagForm,1);
    }

    /**
     * 按照更新时间查询 交友项目
     * @param pageable 分页
     * @return
     */
    public List<JSONObject> findMakeFriendProjectsOrderByUpdateTime(Pageable pageable) {
        Page<MakeFriendProject> page = makeFriendProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<JSONObject> list = new ArrayList<>();

        for (MakeFriendProject makeFriendProject: page){
            MakeFriendProjectDTO makeFriendProjectDTO = new MakeFriendProjectDTO();
            BeanUtils.copyProperties(makeFriendProject, makeFriendProjectDTO,"picture");
            if(makeFriendProject.getPicture()!=null){
                makeFriendProjectDTO.setPicture(makeFriendProject.getPicture().split(","));
            }
            JSONObject rewardProjectInfo = JSONObject.parseObject(makeFriendProjectDTO.toString());
            UserInfoDTO userInfo = userInfoService.findUserInfoByUserOpeinid(makeFriendProjectDTO.getUserOpenId());
            rewardProjectInfo.put("headPortrait",userInfo.getHeadPortrait());
            rewardProjectInfo.put("userName",userInfo.getUserName());
            rewardProjectInfo.put("major",userInfo.getUserMajor());
            rewardProjectInfo.put("university",userInfo.getUserUniversity());
            rewardProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(makeFriendProject.getProjectId()));
            list.add(rewardProjectInfo);
        }
        return list;
    }

    /**
     * 按照热度查询
     * @param pageable 分页请求
     * @return
     */
    public List<MakeFriendProjectDTO> findMakeFriendProjectOrderByFavoritesNumber(Pageable pageable) {
        Page<MakeFriendProject> page = makeFriendProjectRepository.findByOrderByFavoriteNumberDesc(pageable);
        List<MakeFriendProjectDTO> list = new ArrayList<>();
        for(MakeFriendProject makeFriendProject : page){
            MakeFriendProjectDTO makeFriendProjectDTO = new MakeFriendProjectDTO();
            BeanUtils.copyProperties(makeFriendProject, makeFriendProjectDTO,"picture");
            if(makeFriendProject.getPicture()!=null){
                makeFriendProjectDTO.setPicture(makeFriendProject.getPicture().split(","));
            }
            list.add(makeFriendProjectDTO);
        }
        return list;
    }

    /**
     * 综合排序
     * @param pageable 分页请求
     * @return
     */
    public List<MakeFriendProjectDTO> findByComplexService(Pageable pageable) {
        List<MakeFriendProjectDTO> result = new ArrayList<>();
        int pageSize = pageable.getPageSize();
        int offsetNumber = pageable.getOffset();
        List<MakeFriendProject> page = makeFriendProjectRepository.findByComplex(pageSize, offsetNumber);
        for(MakeFriendProject makeFriendProject : page){
            MakeFriendProjectDTO makeFriendProjectDTO = new MakeFriendProjectDTO();
            BeanUtils.copyProperties(makeFriendProject, makeFriendProjectDTO,"picture");
            if(makeFriendProject.getPicture()!=null){
                makeFriendProjectDTO.setPicture(makeFriendProject.getPicture().split(","));
            }
            result.add(makeFriendProjectDTO);
        }
        return result;
    }

    /**
     * 根据标题搜索
     * @param titleKeyword 标题关键字
     * @param pageable 分页请求
     * @return
     */
    public List<MakeFriendProjectDTO> findByTitleLike(String titleKeyword,Pageable pageable){
        List<MakeFriendProjectDTO> list = new ArrayList<>();
        Page<MakeFriendProject> page = makeFriendProjectRepository.findByTitleLike("%" + titleKeyword + "%", pageable);
        for(MakeFriendProject makeFriendProject : page){
            MakeFriendProjectDTO makeFriendProjectDTO = new MakeFriendProjectDTO();
            BeanUtils.copyProperties(makeFriendProject, makeFriendProjectDTO,"picture");
            if(makeFriendProject.getPicture()!=null){
                makeFriendProjectDTO.setPicture(makeFriendProject.getPicture().split(","));
            }
            list.add(makeFriendProjectDTO);
        }
        return list;
    }
}
