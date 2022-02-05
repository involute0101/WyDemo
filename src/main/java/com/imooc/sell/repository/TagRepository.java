package com.imooc.sell.repository;

import com.imooc.sell.dataobject.RewardProject;
import com.imooc.sell.dataobject.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findByUserOpenId(String userOpenId);
}
