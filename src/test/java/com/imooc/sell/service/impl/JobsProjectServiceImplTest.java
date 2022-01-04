package com.imooc.sell.service.impl;

import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.JobsProjectDTO;
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
public class JobsProjectServiceImplTest {
    @Autowired
    JobsProjectServiceImpl jobsProjectService;


    @Test
    public void findJobsProjectsOrderByUpdateTime() {
        PageRequest pageRequest = new PageRequest(0,2);
        Assert.assertNotNull(jobsProjectService.findJobsProjectsOrderByUpdateTime(pageRequest));
    }

    @Test
    public void findJobsProjectByProjectId() {
        Assert.assertNotNull(jobsProjectService.findJobsProjectByProjectId("1598241834121721346"));
    }
}