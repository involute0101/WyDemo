package com.imooc.sell.repository;

import com.imooc.sell.dataobject.DailyProject;
import com.imooc.sell.dataobject.MakeFriendProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DailyProjectRepository extends JpaRepository<DailyProject,Integer> {
    Page<DailyProject> findByOrderByUpdateTimeDesc(Pageable pageable);

    Page<DailyProject> findByOrderByFavoriteNumberDesc(Pageable pageable);

    @Query(value = "select * from daily_project order by update_time*0.000001+favorite_number*10 desc limit ?1 offset ?2",
            nativeQuery = true)
    List<DailyProject> findByComplex(Integer pageSize, Integer offsetNumber);

    Page<DailyProject> findByTitleLike(String keyword, Pageable pageable);

    DailyProject findByProjectId(String projectId);
}
