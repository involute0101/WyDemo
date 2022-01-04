package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProjectMaster;
import com.imooc.sell.dataobject.WyMissionProject;
import com.imooc.sell.utils.KeyUtil;
import com.tencentcloudapi.msp.v20180319.models.Project;
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
public class WyMissionProjectRepositoryTest {
    @Autowired
    WyMissionProjectRepository wyMissionProjectRepository;

    @Autowired
    ProjectMasterRepository projectMasterRepository;

    @Test
    public void save(){
        WyMissionProject wyMissionProject =  new WyMissionProject();
        wyMissionProject.setAmount(new BigDecimal(2));
        wyMissionProject.setTitle("TEST");
        wyMissionProject.setContent("TEST");
        wyMissionProject.setHyperlink("111.com");
        wyMissionProject.setLocation("无要求");
        String PId = KeyUtil.genUniqueKey();
        wyMissionProject.setProjectId(PId);
        ProjectMaster projectMaster = new ProjectMaster();
        projectMaster.setProjectId(PId);
        projectMaster.setProjectType(4);
        projectMaster.setUserId(3);
        projectMasterRepository.save(projectMaster);
        Assert.assertNotNull(wyMissionProjectRepository.save(wyMissionProject));
    }
    @Test
    public void findByProjectId() {
        Assert.assertNotNull(wyMissionProjectRepository.findByProjectId("1602493680070775712"));
    }

    @Test
    public void findByOrderByUpdateTimeDesc() {
        Assert.assertNotNull(wyMissionProjectRepository.findByOrderByUpdateTimeDesc(new PageRequest(0,1)));
    }

    @Test
    public void findByTitle() {
        Assert.assertNotNull(wyMissionProjectRepository.findByTitle("TEST"));
    }

    @Test
    public void findByLocation() {
        Assert.assertNotNull(wyMissionProjectRepository.findByLocation("无要求"));
    }
}