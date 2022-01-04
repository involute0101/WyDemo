package com.imooc.sell.repository;

import com.imooc.sell.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;


public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    UserInfo findByUserOpenid(String openid);

    List<UserInfo> findByUserIdIn(Collection<Integer> c);
}
