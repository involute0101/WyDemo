package com.imooc.sell.repository;

import com.imooc.sell.dataobject.LeaveMessageLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveMessageLikeRepository extends JpaRepository<LeaveMessageLike, Integer> {
    LeaveMessageLike findByUserOpenIdAndLeaveMessageId(String userOpenId, Integer leaveMessageId);
}
