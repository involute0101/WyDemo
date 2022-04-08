package com.imooc.sell.service.impl;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dataobject.UserFollow;
import com.imooc.sell.dataobject.UserInfo;
import com.imooc.sell.dto.UserFollowDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.UserFollowRepository;
import com.imooc.sell.repository.UserInfoRepository;
import com.imooc.sell.service.UserFollowService;
import com.imooc.sell.service.UserInfoService;
import com.imooc.sell.utils.ResultVOUtil;
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

    @Autowired
    UserInfoRepository userInfoRepository;

    /**
     * 创建一个关注
     * @param userFollowDTO
     * @return 关注结果
     */
    @Override
    public ResultVO createFollow(UserFollowDTO userFollowDTO) {
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
            userFollowRepository.delete(userFollowCheck.getId());
            return ResultVOUtil.success("取消关注！");
        }
        UserFollow userFollow = new UserFollow();
        BeanUtils.copyProperties(userFollowDTO,userFollow);
        logger.info("用户"+userInfoDTO.getUserOpenid()+"正在关注"+userFollowDTO.getGoalFollower());
        UserFollow save = userFollowRepository.save(userFollow);
        UserFollowDTO resultDTO = new UserFollowDTO();
        BeanUtils.copyProperties(save,resultDTO);
        return ResultVOUtil.success(resultDTO);
    }

    /**
     * 根据openId判断，是否关注某用户
     * @param userOpenId 用户openId
     * @param goalUserOpenId 目标关注用户openId
     * @return
     */
    @Override
    public ResultVO checkUserFollow(String userOpenId, String goalUserOpenId) {
        if(userOpenId.equals(goalUserOpenId)){
            throw new SellException(ResultEnum.SAME_OPENID);
        }
        UserInfo userInfo = userInfoRepository.findByUserOpenid(userOpenId);
        if(userInfo==null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        UserInfo goalUserInfo = userInfoRepository.findByUserOpenid(goalUserOpenId);
        if(goalUserInfo==null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        UserFollow userFollow = userFollowRepository.findByUserOpenIdAndGoalFollower(userOpenId, goalUserOpenId);
        if(userFollow!=null)return ResultVOUtil.success(true);
        return ResultVOUtil.success(false);
    }
}
