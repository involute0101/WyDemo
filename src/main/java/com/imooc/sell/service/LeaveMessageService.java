package com.imooc.sell.service;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.dto.LeaveMessageDTO;
import com.imooc.sell.dto.LeaveMessageLikeDTO;

import java.util.List;

public interface LeaveMessageService {
    LeaveMessageDTO createOne(LeaveMessageDTO leaveMessageDTO);

    List<LeaveMessageDTO> findByOpenId(String userOpenId);

    List<LeaveMessageDTO> findByProjectId(String projectId);

    boolean deleteOne(String userOpenId, String projectId, Integer key);

    LeaveMessageDTO changeOne(String userOpenId, String projectId, String content, Integer key);

    JSONObject likeLeaveMessageOrNot(LeaveMessageLikeDTO leaveMessageLikeDTO);

    JSONObject checkLikeOrNot(Integer leaveMessageId,String userOpenId);
}
