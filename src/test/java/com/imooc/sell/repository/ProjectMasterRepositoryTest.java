package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProjectMaster;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class ProjectMasterRepositoryTest {
    @Autowired ProjectMasterRepository projectMasterRepository;

    @Test
    public void save(){
        for (int i =0; i <10; i++){
        ProjectMaster projectMaster = new ProjectMaster();
        projectMaster.setProjectId(KeyUtil.genUniqueKey());
        projectMaster.setUserId(3);
        projectMaster.setProjectType(6);
        projectMasterRepository.save(projectMaster);
        }
    }

    @Test
    public void findByProjectId() {
        Assert.assertNotNull(projectMasterRepository.findByUserId(3));
    }

    @Test
    public void findByUserId() {
        List<ProjectMaster> projectMaster = projectMasterRepository.findByUserId(3);
        Assert.assertNotNull(projectMasterRepository.findByProjectId(projectMaster.get(0).getProjectId()));
    }

    @Test
    public void deleteByProjectId(){
        projectMasterRepository.deleteByProjectId("1597723259445899631");
    }
}