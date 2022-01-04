package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProjectMaster;
import com.imooc.sell.dataobject.PurchasingProject;
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
public class PurchasingProjectRepositoryTest {
    @Autowired
    PurchasingProjectRepository purchasingProjectRepository;

    @Autowired
    ProjectMasterRepository projectMasterRepository;

    @Test
    public void save(){
        PurchasingProject purchasingProject = new PurchasingProject();
        ProjectMaster projectMaster = new ProjectMaster();
        projectMaster.setUserId(3);
        projectMaster.setProjectType(1);
        String projectId = KeyUtil.genUniqueKey();
        projectMaster.setProjectId(projectId);
        projectMasterRepository.save(projectMaster);

        purchasingProject.setProjectId(projectId);
        purchasingProject.setTitle("带只猪");
        purchasingProject.setAmount(new BigDecimal(10));
        purchasingProject.setContactNumber("15770216");
        purchasingProject.setContent("带只猪给我");
        purchasingProject.setLocation("信息学部11");
        Assert.assertNotNull(purchasingProjectRepository.save(purchasingProject));

    }

    @Test
    public void findByProjectId() {
        Assert.assertNotNull(purchasingProjectRepository.findByProjectId("1601616040498362140"));
    }

    @Test
    public void findByOrderByUpdateTimeDesc() {
        Assert.assertNotNull(purchasingProjectRepository.findByOrderByUpdateTimeDesc(new PageRequest(0,2)));
    }

    @Test
    public void findByTitle() {
        Assert.assertNotNull(purchasingProjectRepository.findByTitle("带只猪"));
    }

    @Test
    public void findByLocation() {
        Assert.assertNotNull(purchasingProjectRepository.findByLocation("信息学部11"));
    }
}