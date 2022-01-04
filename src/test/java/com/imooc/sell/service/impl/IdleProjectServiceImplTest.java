package com.imooc.sell.service.impl;

import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.service.IdleProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class IdleProjectServiceImplTest {
    @Autowired
    IdleProjectServiceImpl idleProjectService;


    @Test
    public void findIdleProjectsOrderByUpdateTime() {
        PageRequest pageRequest = new PageRequest(0,5);
        List<IdleProjectDTO> idleProjectDTOList = idleProjectService.findIdleProjectsOrderByUpdateTime(pageRequest);
        Assert.assertNotNull(idleProjectDTOList);

    }


    @Test
    public void findIdleProjectByProjectId(){
    IdleProjectDTO idleProjectDTO =  idleProjectService.findIdleProjectByProjectId("1598200360959685504");
    Assert.assertNotNull(idleProjectDTO);
    }
}