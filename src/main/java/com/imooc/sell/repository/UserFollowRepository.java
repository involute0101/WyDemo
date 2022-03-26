package com.imooc.sell.repository;

import com.imooc.sell.dataobject.UserFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow, Integer> {
    UserFollow findByUserOpenIdAndGoalFollower(String userOpenId,String goalFollower);

    Page<UserFollow> findByUserOpenId(String userOpenId, Pageable pageable);
}
