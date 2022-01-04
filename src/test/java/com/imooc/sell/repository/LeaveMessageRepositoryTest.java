package com.imooc.sell.repository;

import com.imooc.sell.dataobject.Favorites;
import com.imooc.sell.dataobject.LeaveMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class LeaveMessageRepositoryTest {
    @Autowired
    LeaveMessageRepository leaveMessageRepository;
    @Test
    public void save(){
        LeaveMessage leaveMessage = new LeaveMessage();
        leaveMessage.setProjectId("1599286362028845205");
        leaveMessage.setUserId(15);
        leaveMessage.setContent("Test01");
        Assert.assertNotNull(leaveMessageRepository.save(leaveMessage));
    }
    @Test
    public void findByProjectId() {
        Assert.assertNotNull(leaveMessageRepository.findByProjectId("1599286362028845205"));
    }

    @Test
    public void findByUserId() {
        Assert.assertNotNull(leaveMessageRepository.findByUserId(15));

    }
    @Test
    public void findByUserIdAndProjectId(){
        Assert.assertNotNull(leaveMessageRepository.findByUserIdAndProjectId(15,"1599286362028845205"));
    }
    @Test
    @Transactional
    @Rollback(false)
    public void deleteByid() {
        leaveMessageRepository.deleteById(3);
    }
}