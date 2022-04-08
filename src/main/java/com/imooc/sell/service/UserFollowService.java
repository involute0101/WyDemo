package com.imooc.sell.service;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dto.UserFollowDTO;

public interface UserFollowService {
    ResultVO createFollow(UserFollowDTO userFollowDTO);

    ResultVO checkUserFollow(String userOpenId,String goalUserOpenId);
}
