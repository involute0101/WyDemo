package com.imooc.sell.repository;

import com.imooc.sell.dataobject.Favorites;
import com.imooc.sell.dataobject.map.Favorites_Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites, Favorites_Map> {
    List<Favorites> findByProjectId(String projectId);

    List<Favorites> findByUserId(Integer userId);

//    List<Favorites> findByUserIdIn(Collection<Integer> c);

    Favorites findByUserIdAndProjectId(Integer userId, String projectId);

    Integer deleteByProjectIdAndUserId(String projectId, Integer userId);

    Favorites findByProjectIdAndUserId(String projectId, Integer userId);

    int countByProjectId(String projectId);

    int countByUserId(Integer userId);


}
