package com.imooc.sell.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.controller.form.TagForm;
import com.imooc.sell.dataobject.Tag;
import com.imooc.sell.dataobject.UserFollow;
import com.imooc.sell.dto.*;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.TagRepository;
import com.imooc.sell.repository.UserFollowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private IdleProjectServiceImpl idleProjectService;

    @Autowired
    private JobsProjectServiceImpl jobsProjectService;

    @Autowired
    private LostPropertyProjectServiceImpl lostPropertyProjectService;

    @Autowired
    private PurchasingServiceImpl purchasingService;

    @Autowired
    private RewardServiceImpl rewardService;

    @Autowired
    private StudyServiceImpl studyService;

    @Autowired
    private UserFollowRepository userFollowRepository;

    @Autowired
    private ProjectMasterServiceImpl projectMasterService;

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
    public List<Tag> createTag(TagForm tagForm,int postInit){
        List<Tag> tags = new ArrayList<>();
        if(tagForm.getTagContent()!=null){
            for(String tagContent : tagForm.getTagContent()){
                if(tagExist(tagContent))continue;
                Tag tag = new Tag();
                tag.setUserOpenId(tagForm.getUserOpenId());
                tag.setTagContent(tagContent);
                tag.setPersonNumber(1);
                tag.setPostNumber(postInit);
                tags.add(tagRepository.save(tag)) ;
            }
        }
        return tags;
    }

    /**
     * 根据关键字查找标签
     * @param keyword 标签关键词
     * @param pageable 分页请求
     * @return
     */
    public List<Tag> findByTagContentLike(String keyword, Pageable pageable){
        List<Tag> tags = new ArrayList<>();
        Page<Tag> page = tagRepository.findByTagContentLike("%" + keyword + "%", pageable);
        for(Tag tag : page)tags.add(tag);
        return tags;
    }

    /**
     * 判断标签是否存在（向上兼容）
     * @param keyword 标签关键字
     * @return
     */
    public boolean tagExist(String keyword){
        PageRequest pageRequest = new PageRequest(0,1);
        Page<Tag> page = tagRepository.findByTagContentLike("%"+keyword+"%", pageRequest);
        if(page.getContent().isEmpty())return false;
        return true;
    }

    /**
     * 根据标签查找所有类型项目
     * @param keyword 标签关键词
     * @param page 页数
     * @param size 页大小
     * @return
     */
    public JSONObject findProjectByTag(String keyword,int page,int size){
        JSONObject result = new JSONObject();
        PageRequest pageRequest = new PageRequest(page-1,size);
        List<IdleProjectDTO> idleProjectDTOList = idleProjectService.findIdleProjectByTagsLike(keyword, pageRequest);
        List<JobsProjectDTO> jobsProjectDTOList = jobsProjectService.findJobsProjectByTagsLike(keyword, pageRequest);
        List<LostPropertyProjectDTO> lostPropertyProjectDTOList = lostPropertyProjectService.findLostPropertyProjectByTagsLike(keyword, pageRequest);
        List<PurchasingProjectDTO> purchasingProjectDTOList = purchasingService.findPurchasingProjectByTagsLike(keyword, pageRequest);
        List<RewardProjectDTO> rewardProjectDTOList = rewardService.findRewardProjectLikeTags(keyword, pageRequest);
        List<StudyProjectDTO> studyProjectDTOList = studyService.findStudyProjectByTagsLike(keyword, pageRequest);
        result.put("IdleProject",idleProjectDTOList);
        result.put("JobsProject",jobsProjectDTOList);
        result.put("LostPropertyProject",lostPropertyProjectDTOList);
        result.put("PurchasingProject",purchasingProjectDTOList);
        result.put("RewardProject",rewardProjectDTOList);
        result.put("StudyProject",studyProjectDTOList);
        return result;
    }

    /**
     * 根据标签查找所有类型项目,并且按照时间排序（最新）
     * @param keyword 标签关键字
     * @param page 页数
     * @param size  页大小
     * @return
     */
    public JSONObject findProjectByTagOrderByTime(String keyword,int page,int size){
        JSONObject result = new JSONObject();
        PageRequest pageRequest = new PageRequest(page-1,size);
        List<IdleProjectDTO> idleProjectDTOList = idleProjectService.findIdleProjectByTagsLikeOrderByUpdateTime(keyword,pageRequest);
        List<JobsProjectDTO> jobsProjectDTOList = jobsProjectService.findJobsProjectByTagsLikeOrderByUpdateTime(keyword,pageRequest);
        List<LostPropertyProjectDTO> lostPropertyProjectDTOList = lostPropertyProjectService.findLostPropertyProjectByTagsLikeOrderByUpdateTime(keyword,pageRequest);
        List<PurchasingProjectDTO> purchasingProjectDTOList = purchasingService.findPurchasingProjectByTagsLikeOrderByUpdateTime(keyword,pageRequest);
        List<RewardProjectDTO> rewardProjectDTOList = rewardService.findRewardProjectByTagsLikeOrderByUpdateTime(keyword,pageRequest);
        List<StudyProjectDTO> studyProjectDTOList = studyService.findStudyProjectByTagsLikeOrderByUpdateTime(keyword,pageRequest);
        result.put("IdleProject",idleProjectDTOList);
        result.put("JobsProject",jobsProjectDTOList);
        result.put("LostPropertyProject",lostPropertyProjectDTOList);
        result.put("PurchasingProject",purchasingProjectDTOList);
        result.put("RewardProject",rewardProjectDTOList);
        result.put("StudyProject",studyProjectDTOList);
        return result;
    }

    /**
     * 根据标签查找所有类型项目,并且按照热度排序（最热）
     * @param keyword 标签关键字
     * @param page 页数
     * @param size  页大小
     * @return
     */
    public JSONObject findProjectByTagOrderByFavoriteNumber(String keyword,int page,int size){
        JSONObject result = new JSONObject();
        PageRequest pageRequest = new PageRequest(page-1,size);
        List<IdleProjectDTO> idleProjectDTOList = idleProjectService.findIdleProjectByTagsLikeOrderByFavoritesNumber(keyword,pageRequest);
        List<JobsProjectDTO> jobsProjectDTOList = jobsProjectService.findJobsProjectByTagsLikeOrderByFavoritesNumber(keyword,pageRequest);
        List<LostPropertyProjectDTO> lostPropertyProjectDTOList = lostPropertyProjectService.findLostPropertyProjectByTagsLikeOrderByFavoritesNumber(keyword,pageRequest);
        List<PurchasingProjectDTO> purchasingProjectDTOList = purchasingService.findPurchasingProjectByTagsLikeOrderByFavoritesNumber(keyword,pageRequest);
        List<RewardProjectDTO> rewardProjectDTOList = rewardService.findRewardProjectByTagsLikeOrderByFavoritesNumber(keyword,pageRequest);
        List<StudyProjectDTO> studyProjectDTOList = studyService.findStudyProjectByTagsLikeOrderByFavoritesNumber(keyword,pageRequest);
        result.put("IdleProject",idleProjectDTOList);
        result.put("JobsProject",jobsProjectDTOList);
        result.put("LostPropertyProject",lostPropertyProjectDTOList);
        result.put("PurchasingProject",purchasingProjectDTOList);
        result.put("RewardProject",rewardProjectDTOList);
        result.put("StudyProject",studyProjectDTOList);
        return result;
    }

    /**
     * 得到圈子信息
     * @param circleName 圈子名称
     * @return
     */
    public Tag getDiscussionCircleInfo(String circleName){
        Tag circle = tagRepository.findByTagContent(circleName);
        if(circle==null){
            throw new SellException(ResultEnum.CIRCLE_NOT_FOUND);
        }
        return circle;
    }

    /**
     * 通过用户自己的openId，查找所有关注的用户发布的项目，按标签搜索
     * @param userOpenId
     * @param tag
     */
    public JSONObject findGoalOpenIdProjectByTag(String userOpenId,String tag,int page,int size){
        JSONObject result = new JSONObject();
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<UserFollow> userFollows = userFollowRepository.findByUserOpenId(userOpenId, pageRequest);
        for(UserFollow userFollow : userFollows){
            String goalUserOpenId = userFollow.getGoalFollower();
            JSONObject userJson = new JSONObject();
            List<ProjectMasterDTO> projectMasterDTOS = projectMasterService.findProjectMasterByUserOpenId(goalUserOpenId);
            for(ProjectMasterDTO projectMasterDTO: projectMasterDTOS){
                Integer projectType = projectMasterDTO.getProjectType();
                JSONArray jsonArray = userJson.getJSONArray(projectType.toString());
                if(jsonArray==null)jsonArray = new JSONArray();
                try{
                    switch (projectType){
                        case 1:
                            PurchasingProjectDTO purchasing = purchasingService.findPurchasingByProjectId(projectMasterDTO.getProjectId());
                            if(purchasing!=null && purchasing.getTags()!=null && purchasing.getTags().contains(tag))jsonArray.add(purchasing);
                            break;
                        case 2:
                            RewardProjectDTO rewardProjectDTO = rewardService.findRewardByProjectId(projectMasterDTO.getProjectId());
                            if(rewardProjectDTO!=null && rewardProjectDTO.getTags()!=null && rewardProjectDTO.getTags().contains(tag))jsonArray.add(rewardProjectDTO);
                            break;
                        case 3:
                            StudyProjectDTO studyProjectDTO = studyService.findStudyByProjectId(projectMasterDTO.getProjectId());
                            if(studyProjectDTO!=null && studyProjectDTO.getTags()!=null && studyProjectDTO.getTags().contains(tag))jsonArray.add(studyProjectDTO);
                            break;
                        case 4:
                            IdleProjectDTO idleProjectDTO = idleProjectService.findIdleProjectByProjectId(projectMasterDTO.getProjectId());
                            if (idleProjectDTO!=null && idleProjectDTO.getTags()!=null && idleProjectDTO.getTags().contains(tag))jsonArray.add(idleProjectDTO);
                            break;
                        case 5:
                            LostPropertyProjectDTO lostPropertyProjectDTO = lostPropertyProjectService.findLostPropertyProjectByProjectId(projectMasterDTO.getProjectId());
                            if(lostPropertyProjectDTO!=null && lostPropertyProjectDTO.getTags()!=null && lostPropertyProjectDTO.getTags().contains(tag))jsonArray.add(lostPropertyProjectDTO);
                            break;
                        case 6:
                            JobsProjectDTO jobsProjectDTO = jobsProjectService.findJobsProjectByProjectId(projectMasterDTO.getProjectId());
                            if(jobsProjectDTO!=null && jobsProjectDTO.getTags()!=null && jobsProjectDTO.getTags().contains(tag))jsonArray.add(jobsProjectDTO);
                            break;
                    }
                    userJson.put(projectType.toString(),jsonArray);
                }catch (SellException e){

                }
            }
            result.put(goalUserOpenId,userJson);
        }
        return result;
    }
}
