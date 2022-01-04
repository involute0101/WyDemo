package com.imooc.sell.service.impl;

import com.imooc.sell.dto.PurchasingProjectDTO;
import com.imooc.sell.dto.RewardProjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class RewardServiceImplTest {
    @Autowired
    RewardServiceImpl rewardService;

    @Test
    public void createRewardProject() throws Exception {
        RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
        rewardProjectDTO.setTitle("悬赏一百万");
        rewardProjectDTO.setUserOpenid("55555555556");
        rewardProjectDTO.setContactNumber("1123123");
        rewardProjectDTO.setAmount(new BigDecimal(100));
        rewardProjectDTO.setLocation("信息学部");
        rewardProjectDTO.setContent("悬赏一只猪的狗头");
        Assert.assertNotNull(rewardService.createRewardProject(rewardProjectDTO));
    }

    @Test
    public void findRewardProjectsOrderByUpdateTime() {
        Assert.assertNotNull(rewardService.findRewardProjectsOrderByUpdateTime(new PageRequest(0,2)));
    }

    @Test
    public void findRewardByProjectId() {
        Assert.assertNotNull("1601882836225832189");
    }
}