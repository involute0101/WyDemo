package com.imooc.sell.repository;

import com.imooc.sell.dataobject.LectureProject;
import com.imooc.sell.dataobject.LostPropertyProject;
import com.imooc.sell.dataobject.ProjectMaster;
import com.imooc.sell.service.impl.ProjectMasterServiceImpl;
import com.imooc.sell.utils.KeyUtil;
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
public class LostPropertyProjectRepositoryTest {
    @Autowired
    LostPropertyProjectRepository lostPropertyProjectRepository;
    @Autowired
    ProjectMasterRepository projectMasterRepository;

//    @Test
//    public void save(){
//        ProjectMaster projectMaster = new ProjectMaster();
//        String key = KeyUtil.genUniqueKey();
//        projectMaster.setProjectId(key);
//        projectMaster.setProjectType(5);
//        projectMaster.setUserId(3);
//        projectMasterRepository.save(projectMaster);
//        LostPropertyProject lostPropertyProject = new LostPropertyProject();
//        lostPropertyProject.setProjectId(key);
//        lostPropertyProject.setTitle("丢失校园卡一张");
//        lostPropertyProject.setAmount(new BigDecimal(10));
//        lostPropertyProject.setContent("今天在教五丢失一张校园卡 请捡到的同学联系我！");
//        lostPropertyProject.setLocation("教五");
//        lostPropertyProject.setContactNumber("15622345671");
//        Assert.assertNotNull(lostPropertyProjectRepository.save(lostPropertyProject));
//    }

    @Test
    public void findByProjectId() {
        Assert.assertNotNull(projectMasterRepository.findByProjectId("1601283719983114655"));

    }

    @Test
    public void findByOrderByUpdateTimeDesc() {
        PageRequest pageRequest = new PageRequest(0,1);
        Assert.assertNotNull(lostPropertyProjectRepository.findByOrderByUpdateTimeDesc(pageRequest));
    }

    @Test
    public void findByTitle() {
        Assert.assertNotNull(lostPropertyProjectRepository.findByTitle("丢失校园卡一张"));
    }

    @Test
    public void findByLocation() {
        Assert.assertNotNull(lostPropertyProjectRepository.findByLocation("教五"));
    }
}