package com.imooc.sell.repository;

import com.imooc.sell.dataobject.IdleProject;
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

import javax.persistence.Id;
import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class IdleProjectRepositoryTest {
    @Autowired IdleProjectRepository idleProjectRepository;
    @Autowired ProjectMasterRepository projectMasterRepository;
    @Test
    public void save(){
        List<ProjectMaster> list =  projectMasterRepository.findByProjectType(6);
        for (int i = 0; i < 10; i++){
            ProjectMaster projectMaster = list.get(i);
            IdleProject idleProject = new IdleProject();
            idleProject.setProjectId(projectMaster.getProjectId());
            idleProject.setTitle("Test"+ i );
            idleProjectRepository.save(idleProject);
        }


    }
    @Test
    public void findByProjectId() {
    }

    @Test
    public void findByTitle() {
        Assert.assertNotNull(idleProjectRepository.findByTitle("出一台洗衣机"));
    }

    @Test
    public void findByLocation() {
        Assert.assertNotNull(idleProjectRepository.findByLocation("枫14"));
    }



    @Test
    public void findByUpdateTimeDesc(){
        PageRequest pageable = new PageRequest(0,5);
        Page<IdleProject> page = idleProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        System.out.println(page.getContent());
        Assert.assertNotNull(page);

    }
}