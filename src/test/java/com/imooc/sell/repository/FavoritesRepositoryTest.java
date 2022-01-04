package com.imooc.sell.repository;

import com.imooc.sell.dataobject.Favorites;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class FavoritesRepositoryTest {
    @Autowired
    FavoritesRepository favoritesRepository;

    @Test
    public void save(){
        Favorites favorites = new Favorites();
        favorites.setProjectId("1599293376422301147");
        favorites.setUserId(3);
        Assert.assertNotNull(favoritesRepository.save(favorites));
    }
    @Test
    public void findByProjectId() {
        Assert.assertNotNull(favoritesRepository.findByProjectId("1599286362028845205"));
    }

    @Test
    public void findByUserId() {
        Assert.assertNotNull(favoritesRepository.findByUserId(3));
    }

    @Test
    public void findByUserIdAndProjectId() {
        Assert.assertNotNull(favoritesRepository.findByUserIdAndProjectId(3,"1599286362028845205"));
    }
}