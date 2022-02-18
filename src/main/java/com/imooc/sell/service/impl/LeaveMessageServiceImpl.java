package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.LeaveMessage;
import com.imooc.sell.dataobject.ProjectMaster;
import com.imooc.sell.dataobject.UserInfo;
import com.imooc.sell.dto.LeaveMessageDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
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
    UserInfoRepository userInfoRepository;

    @Autowired
    ProjectMasterRepository projectMasterRepository;


    @Override
    @Transactional
    public LeaveMessageDTO createOne(LeaveMessageDTO leaveMessageDTO) {
        //检查userId
        UserInfo userInfo = userInfoRepository.findByUserId(leaveMessageDTO.getUserId());
        if (userInfo == null) {
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
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
            return leaveMessageDTO;
        }
        else
            return null;
    }

}
