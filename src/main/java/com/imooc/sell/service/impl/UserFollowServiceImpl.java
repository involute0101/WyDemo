package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.UserFollow;
import com.imooc.sell.dto.UserFollowDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.UserFollowRepository;
import com.imooc.sell.service.UserFollowService;
import com.imooc.sell.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserFollowServiceImpl implements UserFollowService {

    public static final Logger logger = LoggerFactory.getLogger(UserFollowServiceImpl.class);

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserFollowRepository userFollowRepository;

    /**
     * 创建一个关注
     * @param userFollowDTO
     * @return 关注结果
     */
    @Override
    public UserFollowDTO createFollow(UserFollowDTO userFollowDTO) {
        if(userFollowDTO.getUserOpenId().equals(userFollowDTO.getGoalFollower())){
            throw new SellException(ResultEnum.SAME_OPENID);
        }
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(userFollowDTO.getUserOpenId());
        UserInfoDTO goalUserInfoDTO = userInfoService.findUserInfoByUserOpeinid(userFollowDTO.getGoalFollower());
        if(userInfoDTO==null || goalUserInfoDTO==null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        UserFollow userFollowCheck = userFollowRepository.findByUserOpenIdAndGoalFollower(userFollowDTO.getUserOpenId(), userFollowDTO.getGoalFollower());
        if(userFollowCheck!=null){
            throw new SellException(ResultEnum.REPEAT_FOLLOW);
        }
        UserFollow userFollow = new UserFollow();
        BeanUtils.copyProperties(userFollowDTO,userFollow);
        logger.info("用户"+userInfoDTO.getUserOpenid()+"正在关注"+userFollowDTO.getGoalFollower());
        UserFollow save = userFollowRepository.save(userFollow);
        UserFollowDTO resultDTO = new UserFollowDTO();
        BeanUtils.copyProperties(save,resultDTO);
        return resultDTO;
    }
}
