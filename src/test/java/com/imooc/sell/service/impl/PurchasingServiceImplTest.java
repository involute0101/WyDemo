package com.imooc.sell.service.impl;

import com.imooc.sell.dto.PurchasingProjectDTO;
import com.imooc.sell.service.PurchasingProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class PurchasingServiceImplTest {
    @Autowired
    PurchasingServiceImpl purchasingService;

    @Test
    public void createPurchasingProject() throws Exception {
        PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();
        purchasingProjectDTO.setUserOpenId("12345678910");
        purchasingProjectDTO.setTitle("带支铅笔");
        purchasingProjectDTO.setLocation("教2");
        purchasingProjectDTO.setAmount(new BigDecimal(10));
        purchasingProjectDTO.setContactNumber("1123121415");
        Assert.assertNotNull(purchasingService.createPurchasingProject(purchasingProjectDTO));
    }

    @Test
    public void findPurchasingProjectsOrderByUpdateTime() {
        List<PurchasingProjectDTO> purchasingProjectDTO = purchasingService.findPurchasingProjectsOrderByUpdateTime(new PageRequest(0,2));
        Assert.assertNotNull(purchasingProjectDTO);
    }

    @Test
    public void findPurchasingByProjectId() {
        Assert.assertNotNull(purchasingService.findPurchasingByProjectId("1601616040498362140"));
    }
}