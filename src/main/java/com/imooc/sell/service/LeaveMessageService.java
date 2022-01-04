package com.imooc.sell.service;

import com.imooc.sell.dto.LeaveMessageDTO;

import java.util.List;

public interface LeaveMessageService {
    LeaveMessageDTO createOne(String userOpenId, String projectId, String content);
    List<LeaveMessageDTO> findByOpenId(String userOpenId);
    List<LeaveMessageDTO> findByProjectId(String projectId);
    boolean deleteOne(String userOpenId, String projectId, Integer key);
    LeaveMessageDTO changeOne(String userOpenId, String projectId, String content, Integer key);
}
