package com.imooc.sell.service.impl;

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
public class LeaveMessageServiceImplTest {
    @Autowired
    LeaveMessageServiceImpl leaveMessageService;
//    @Test
//    public void createOne() {
//        Assert.assertNotNull(leaveMessageService.createOne("55555555555","1599293376422301147","TEST01"));
//    }

    @Test
    public void findByOpenId() {
        Assert.assertNotNull(leaveMessageService.findByOpenId("55555555555"));
    }

    @Test
    public void findByProjectId() {
        Assert.assertNotNull(leaveMessageService.findByProjectId("1599293376422301147"));
    }

    @Test
    public void deleteOne() {
        Assert.assertEquals(true,leaveMessageService.deleteOne("13479058321","1601894949957505676",3));
    }

//    @Test
//    public void changeOne() {
//        Assert.assertNotNull(leaveMessageService.changeOne("55555555555","1599293376422301147","TESTCHANGE01"));
//    }
}