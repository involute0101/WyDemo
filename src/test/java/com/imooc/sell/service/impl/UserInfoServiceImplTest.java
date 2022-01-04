package com.imooc.sell.service.impl;

import com.imooc.sell.dto.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class UserInfoServiceImplTest {
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Test
    public void createUserInfoOne() {
    }

    @Test
    public void findUserInfoByBuyerOpenidAndPassword() {
    }

    @Test
    public void sendSms() {
    }

    @Test
    public void findUserInfoByBuyerOpenid() {
        Assert.assertNotNull(userInfoService.findUserInfoByUserOpeinid("12345677"));
    }

    @Test
    public void findUserInfos(){
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(5);
        List<UserInfoDTO> userInfoDTOS = userInfoService.findUserInfos(list);
        Assert.assertNotNull(userInfoDTOS.get(0));
    }
}