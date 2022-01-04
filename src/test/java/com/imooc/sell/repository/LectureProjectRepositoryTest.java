package com.imooc.sell.repository;

import com.imooc.sell.dataobject.LectureProject;
import com.imooc.sell.dataobject.ProjectMaster;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class LectureProjectRepositoryTest {
    @Autowired
    ProjectMasterRepository projectMasterRepository;
    @Autowired
    LectureProjectRepository lectureProjectRepository;

    @Test
    public void save(){
        LectureProject lectureProject = new LectureProject();
        ProjectMaster projectMaster = new ProjectMaster();
        String key = KeyUtil.genUniqueKey();
        projectMaster.setProjectId(key);
        projectMaster.setProjectType(0);
        projectMaster.setUserId(3);
        projectMasterRepository.save(projectMaster);
        lectureProject.setProjectId(key);
        lectureProject.setTitle("时间管理大师来上课啦");
        Assert.assertNotNull(lectureProjectRepository.save(lectureProject));
    }

    @Test
    public void findByProjectId() {
        Assert.assertNotNull(lectureProjectRepository.findByProjectId("1599286362028845205"));

    }

    @Test
    public void findByOrderByUpdateTimeDesc() {
        PageRequest pageRequest = new PageRequest(0,1);
        Page<LectureProject> lectureProjectList = lectureProjectRepository.findByOrderByUpdateTimeDesc(pageRequest);
        Assert.assertNotNull(lectureProjectList);
    }

    @Test
    public void findByTitle() {
        Assert.assertNotNull(lectureProjectRepository.findByTitle("时间管理大师来上课啦"));
    }

    @Test
    public void findByLocation() {
        Assert.assertNotNull(lectureProjectRepository.findByLocation("工1"));
    }


    @Test
    public void deleteById() {
    }
}