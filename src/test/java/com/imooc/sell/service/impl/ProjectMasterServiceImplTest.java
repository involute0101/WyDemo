package com.imooc.sell.service.impl;

import com.imooc.sell.dto.ProjectMasterDTO;
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
public class ProjectMasterServiceImplTest {
    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Test
    public void createProjectMasterOne() throws Exception {
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(3);
        projectMasterDTO.setProjectType(0);
        Assert.assertNotNull(projectMasterService.createProjectMasterOne(projectMasterDTO));
    }

    @Test
    public void findProjectMasterByUserId() {
        List<ProjectMasterDTO> projectMasterDTOS = projectMasterService.findProjectMasterByUserOpenId("13479058321");
        Assert.assertNotNull(projectMasterDTOS);
    }

    @Test
    public void findProjectMasterByProjectId() {
        List<ProjectMasterDTO> projectMasterDTOS = projectMasterService.findProjectMasterByUserOpenId("13479058321");
        ProjectMasterDTO projectMasterDTO = projectMasterDTOS.get(0);
        String projectId = projectMasterDTO.getProjectId();
        Assert.assertNotNull(projectMasterService.findProjectMasterByProjectId(projectId));
    }

    @Test
    public void setProjectMasterOnProjectType() {
        ProjectMasterDTO result = projectMasterService.setProjectMasterOnProjectType("1597633361916324396", "13479058321",5);
        Integer excp = 5;
        Assert.assertEquals(excp,result.getProjectType());
    }

    @Test
    public void deleateProjectMaster() {
        Boolean result = projectMasterService.deleateProjectMaster("1597633361916324396", "13479058321");
        Assert.assertEquals(true,result);
    }

    @Test
    public void findProjectMasterByUserIdAndProjectType(){
        List<ProjectMasterDTO> projectMasterDTOS = projectMasterService.findProjectMasterByUserOpenIdAndProjectType("13479058321",6);
        Assert.assertNotNull(projectMasterDTOS);
    }
}