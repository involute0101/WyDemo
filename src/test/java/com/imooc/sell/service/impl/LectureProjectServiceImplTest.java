package com.imooc.sell.service.impl;

import com.imooc.sell.dto.LectureProjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class LectureProjectServiceImplTest {
    @Autowired
    LectureProjectServiceImpl lectureProjectService;

    @Test
    public void createLectureProjectOne() throws Exception {
        LectureProjectDTO lectureProjectDTO = new LectureProjectDTO();
        lectureProjectDTO.setTitle("小张告诉你怎么好好打球");
        lectureProjectDTO.setUserOpenid("13479058321");
        LectureProjectDTO result = lectureProjectService.createLectureProjectOne(lectureProjectDTO);
        Assert.assertNotNull(result);
    }

    @Test
    public void findLectureProjectsOrderByUpdateTime() {
        PageRequest pageRequest = new PageRequest(0,2);
        List<LectureProjectDTO> list = lectureProjectService.findLectureProjectsOrderByUpdateTime(pageRequest);
        Assert.assertNotNull(list);
    }

    @Test
    public void findLectureProjectByProjectId() {
        Assert.assertNotNull(lectureProjectService.findLectureProjectByProjectId("1599286362028845205"));
    }
}