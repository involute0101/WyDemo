package com.imooc.sell.repository;

import com.imooc.sell.dataobject.LeaveMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveMessageRepository extends JpaRepository<LeaveMessage, Integer> {
    List<LeaveMessage> findByProjectIdAndLmIdIsNull(String projectId);

    List<LeaveMessage> findByUserId(Integer userId);

    Integer deleteById(Integer id);

    LeaveMessage findByUserIdAndProjectId(Integer userId, String projectId);
    
    List<LeaveMessage> findByLmId(Integer lmId, Pageable pageable);
}
