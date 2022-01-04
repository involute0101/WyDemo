package com.imooc.sell.service.impl;

import com.imooc.sell.dto.LostPropertyProjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class LostPropertyProjectServiceImplTest {
    @Autowired
    LostPropertyProjectServiceImpl lostPropertyProjectService;


//    @Test
//    public void createLostPropertyProject() throws Exception {
//        LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();
//        lostPropertyProjectDTO.setUserOpenid("12345678910");
//        lostPropertyProjectDTO.setContactNumber("1102231231");
//        lostPropertyProjectDTO.setTitle("人没了");
//        lostPropertyProjectDTO.setContent("我丢了个人在学校里");
//        lostPropertyProjectDTO.setLocation("教1");
//        Assert.assertNotNull(lostPropertyProjectService.createLostPropertyProject(lostPropertyProjectDTO));
//
//    }

    @Test
    public void findLostPropertyProjectsOrderByUpdateTime() {
        Assert.assertNotNull(lostPropertyProjectService.findLostPropertyProjectsOrderByUpdateTime(new PageRequest(0,1)));
    }

    @Test
    public void findLostPropertyProjectByProjectId() {
        Assert.assertNotNull(lostPropertyProjectService.findLostPropertyProjectByProjectId("1601348153649169585"));
    }
}