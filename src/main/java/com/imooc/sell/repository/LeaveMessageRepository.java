package com.imooc.sell.repository;

import com.imooc.sell.dataobject.LeaveMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveMessageRepository extends JpaRepository<LeaveMessage,Integer> {
    List<LeaveMessage>findByProjectId(String projectId);
    List<LeaveMessage>findByUserId(Integer userId);
    Integer deleteById(Integer id);
    LeaveMessage findByUserIdAndProjectId(Integer userId, String projectId);
}
