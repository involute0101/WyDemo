package com.imooc.sell.repository;

import com.imooc.sell.dataobject.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class UserInfoRepositoryTest {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Test
    public void saveUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserOpenid("156273614826");
        userInfo.setUserPassword("123456");
        Assert.assertNotNull(userInfoRepository.save(userInfo));
    }
    @Test
    public void findByUserOpenid() {
    Assert.assertNotNull(userInfoRepository.findByUserOpenid("12345677"));
    }
}