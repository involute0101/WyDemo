package com.imooc.sell.repository;

import com.imooc.sell.dataobject.JobsProject;
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
public class JobsProjectRepositoryTest {
    @Autowired
    JobsProjectRepository jobsProjectRepository;

    @Test
    public void save(){
        JobsProject jobsProject = new JobsProject();
        jobsProject.setProjectId("1597723259810901738");
        jobsProject.setTitle("华为招聘开始啦！");
        jobsProject.setLocation("工学部体育馆");
        Assert.assertNotNull(jobsProjectRepository.save(jobsProject));

    }

    @Test
    public void findByProjectId() {
        Assert.assertNotNull(jobsProjectRepository.findByProjectId("1597723259810901738"));
    }

    @Test
    public void findByTitle() {
        Assert.assertNotNull(jobsProjectRepository.findByTitle("华为招聘开始啦！"));
    }

    @Test
    public void findByLocation() {
        Assert.assertNotNull(jobsProjectRepository.findByLocation("工学部体育馆"));
    }



    @Test
    public void findByOrderByUpdatimeDesc(){
        PageRequest pageRequest = new PageRequest(0,5);
        Assert.assertNotNull(jobsProjectRepository.findByOrderByUpdateTimeDesc(pageRequest));
    }
}