package com.imooc.sell.repository;

import com.imooc.sell.dataobject.MakeFriendProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MakeFriendProjectRepository extends JpaRepository<MakeFriendProject,Integer> {
    Page<MakeFriendProject> findByOrderByUpdateTimeDesc(Pageable pageable);

    Page<MakeFriendProject> findByOrderByFavoriteNumberDesc(Pageable pageable);

    @Query(value = "select * from make_friend_project order by update_time*0.000001+favorite_number*10 desc limit ?1 offset ?2",
            nativeQuery = true)
    List<MakeFriendProject> findByComplex(Integer pageSize, Integer offsetNumber);

    Page<MakeFriendProject> findByTitleLike(String keyword, Pageable pageable);
}
