package com.imooc.sell.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.dataobject.LeaveMessage;
import com.imooc.sell.dataobject.LeaveMessageLike;
import com.imooc.sell.dataobject.ProjectMaster;
import com.imooc.sell.dataobject.UserInfo;
import com.imooc.sell.dto.LeaveMessageDTO;
import com.imooc.sell.dto.LeaveMessageLikeDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.LeaveMessageLikeRepository;
import com.imooc.sell.repository.LeaveMessageRepository;
import com.imooc.sell.repository.ProjectMasterRepository;
import com.imooc.sell.repository.UserInfoRepository;
import com.imooc.sell.service.LeaveMessageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class LeaveMessageServiceImpl implements LeaveMessageService {

    public static final Logger logger = LoggerFactory.getLogger(LeaveMessageServiceImpl.class);

    @Autowired
    LeaveMessageRepository leaveMessageRepository;

    @Autowired
    LeaveMessageLikeRepository leaveMessageLikeRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    ProjectMasterRepository projectMasterRepository;


    @Override
    @Transactional
    public LeaveMessageDTO createOne(LeaveMessageDTO leaveMessageDTO) {
        //检查userOpenId
        UserInfo userInfo = userInfoRepository.findByUserOpenid(leaveMessageDTO.getUserOpenid());
        if (userInfo == null) {
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        leaveMessageDTO.setUserId(userInfo.getUserId());
        //检查projetId
        ProjectMaster projectMaster = projectMasterRepository.findByProjectId(leaveMessageDTO.getProjectId());
        if (projectMaster == null) {
            throw new SellException(ResultEnum.PROJECT_ID_NOT_FOUND);
        }
        LeaveMessage leaveMessage = new LeaveMessage();
        BeanUtils.copyProperties(leaveMessageDTO,leaveMessage);
        logger.info("创建留言:"+leaveMessage);
        LeaveMessage result = leaveMessageRepository.save(leaveMessage);
        if (result != null){
            LeaveMessageDTO leaveMessageDTOResult = new LeaveMessageDTO();
            BeanUtils.copyProperties(result,leaveMessageDTOResult);
            leaveMessageDTOResult.setUserOpenid(leaveMessageDTO.getUserOpenid());
            return leaveMessageDTOResult;
        }
        else
            return null;
    }

    @Override
    public List<LeaveMessageDTO> findByOpenId(String userOpenId) {
        //根据OpenId查找UserId
        UserInfo userInfo = userInfoRepository.findByUserOpenid(userOpenId);
        if (userInfo == null) {
            throw new SellException(ResultEnum.PROJECT_ID_NOT_FOUND);
        }

        //根据UserId查找LeaveMessage
        List<LeaveMessage> leaveMessages = leaveMessageRepository.findByUserId(userInfo.getUserId());

        //转成DTO
        ArrayList<LeaveMessageDTO> leaveMessageDTOS = new ArrayList<>();
        for (LeaveMessage leaveMessage : leaveMessages) {
            LeaveMessageDTO leaveMessageDTO = new LeaveMessageDTO();
            BeanUtils.copyProperties(leaveMessage,leaveMessageDTO);
            leaveMessageDTO.setUserOpenid(userOpenId);
            leaveMessageDTOS.add(leaveMessageDTO);
        }
        return leaveMessageDTOS;
    }

    @Override
    public List<LeaveMessageDTO> findByProjectId(String projectId) {
        //验证projectId
        ProjectMaster projectMaster = projectMasterRepository.findByProjectId(projectId);
        if (projectMaster == null) {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }
        //查找LeaveMessages
        List<LeaveMessage> leaveMessages = leaveMessageRepository.findByProjectId(projectId);

        //转成DTOS
        ArrayList<LeaveMessageDTO> leaveMessageDTOS = new ArrayList<>();
        for (LeaveMessage leaveMessage : leaveMessages) {
            LeaveMessageDTO leaveMessageDTO = new LeaveMessageDTO();
            BeanUtils.copyProperties(leaveMessage, leaveMessageDTO);
            leaveMessageDTO.setUserOpenid(userInfoRepository.findByUserId(leaveMessage.getUserId()).getUserOpenid());
            leaveMessageDTOS.add(leaveMessageDTO);

        }
        return leaveMessageDTOS;
    }

    @Override
    @Transactional
    public boolean deleteOne(String userOpenId, String projectId, Integer key) {
        //根据openId查询userId
        UserInfo userInfo = userInfoRepository.findByUserOpenid(userOpenId);
        if (userInfo == null) {
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }

        //查找留言是否存在
        LeaveMessage leaveMessage = leaveMessageRepository.findOne(key);
        if (leaveMessage == null) {
            throw new SellException(ResultEnum.LEAVEMESSAGE_NOT_FOUND);
        }
        if (leaveMessage.getProjectId().equals(projectId) && leaveMessage.getUserId().equals(userInfo.getUserId())) {
            //删除操作
            int deleteResult = leaveMessageRepository.deleteById(key);
            if (deleteResult == 1) {
                return true;
            } else
                return false;
        } else {
            throw new SellException(ResultEnum.USERINFO_OR_PROJECTINFO_NOT_MATCH);
        }
    }

    @Override
    @Transactional
    public LeaveMessageDTO changeOne(String userOpenId, String projectId, String content, Integer key) {
        //根据openId查询userId
        UserInfo userInfo = userInfoRepository.findByUserOpenid(userOpenId);
        if (userInfo == null) {
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }

        //查找留言是否存在
        LeaveMessage leaveMessage = leaveMessageRepository.findOne(key);
        if (leaveMessage == null) {
            throw new SellException(ResultEnum.LEAVEMESSAGE_NOT_FOUND);
        }
        //重新save
        leaveMessage.setId(key);
        leaveMessage.setContent(content);
        LeaveMessage result = leaveMessageRepository.save(leaveMessage);
        if (result != null){
            LeaveMessageDTO leaveMessageDTO = new LeaveMessageDTO();
            BeanUtils.copyProperties(result,leaveMessageDTO);
            leaveMessageDTO.setUserOpenid(userOpenId);
            return leaveMessageDTO;
        }
        else
            return null;
    }

    /**
     * 对留言进行点赞和取消，点赞->取消 || 取消->点赞
     * @param leaveMessageLikeDTO 点赞信息
     * @return
     */
    @Override
    @Transactional
    public JSONObject likeLeaveMessageOrNot(LeaveMessageLikeDTO leaveMessageLikeDTO){
        JSONObject result = new JSONObject();
        UserInfo userInfo = userInfoRepository.findByUserOpenid(leaveMessageLikeDTO.getUserOpenId());
        if (userInfo == null) {
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        LeaveMessageLike leaveMessageLike = leaveMessageLikeRepository.findByUserOpenIdAndLeaveMessageId(
                leaveMessageLikeDTO.getUserOpenId(), leaveMessageLikeDTO.getLeaveMessageId());
        if (leaveMessageLike==null){
            LeaveMessageLike leaveMessageLikeSave = new LeaveMessageLike();
            BeanUtils.copyProperties(leaveMessageLikeDTO,leaveMessageLikeSave);
            LeaveMessageLike saveResult = leaveMessageLikeRepository.save(leaveMessageLikeSave);
            LeaveMessage leaveMessage = leaveMessageRepository.findOne(leaveMessageLikeDTO.getLeaveMessageId());
            leaveMessage.setLikeNumber(leaveMessage.getLikeNumber()+1);
            leaveMessageRepository.save(leaveMessage);
            result.put("message","点赞成功！感谢您的喜欢~~");
            result.put("content",saveResult);
        }else{
            leaveMessageLikeRepository.delete(leaveMessageLike);
            LeaveMessage leaveMessage = leaveMessageRepository.findOne(leaveMessageLikeDTO.getLeaveMessageId());
            leaveMessage.setLikeNumber(leaveMessage.getLikeNumber()-1);
            leaveMessageRepository.save(leaveMessage);
            result.put("message","取消点赞~~");
            result.put("content",leaveMessageLikeDTO);
        }
        return result;
    }

    /**
     * 查询用户是否点赞过该留言
     * @param leaveMessageId    留言id
     * @param userOpenId    用户openId
     * @return
     */
    @Override
    public JSONObject checkLikeOrNot(Integer leaveMessageId, String userOpenId) {
        JSONObject result = new JSONObject();
        UserInfo userInfo = userInfoRepository.findByUserOpenid(userOpenId);
        if (userInfo == null) {
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        LeaveMessage leaveMessage = leaveMessageRepository.findOne(leaveMessageId);
        if(leaveMessage==null){
            throw new SellException(ResultEnum.LEAVEMESSAGE_NOT_FOUND);
        }
        LeaveMessageLike leaveMessageLike = leaveMessageLikeRepository.findByUserOpenIdAndLeaveMessageId(userOpenId, leaveMessageId);
        if(leaveMessageLike==null){
            result.put("message","该用户未点赞过该留言");
            result.put("content",false);
        }else{
            result.put("message","该用户已点赞");
            result.put("content",true);
        }
        return result;
    }
}
