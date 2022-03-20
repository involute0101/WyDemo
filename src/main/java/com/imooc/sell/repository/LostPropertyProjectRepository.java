package com.imooc.sell.repository;

import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dataobject.JobsProject;
import com.imooc.sell.dataobject.LostPropertyProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LostPropertyProjectRepository extends JpaRepository<LostPropertyProject, Integer> {
    LostPropertyProject findByProjectId(String projectId);

    Page<LostPropertyProject> findByOrderByUpdateTimeDesc(Pageable pageable);

    Page<LostPropertyProject> findByTagsLikeOrderByUpdateTimeDesc(String keyword,Pageable pageable);

    Page<LostPropertyProject> findByOrderByFavoriteNumberDesc(Pageable pageable);

    Page<LostPropertyProject> findByOrderByAmount(Pageable pageable);

    Page<LostPropertyProject> findByOrderByAmountDesc(Pageable pageable);

    List<LostPropertyProject> findByTitle(String title);

    List<LostPropertyProject> findByLocation(String location);

    //调用者要在关键字前后加上“%”，以实现模糊查询
    Page<LostPropertyProject> findByTagsLike(String keyword, Pageable pageable);

    Page<LostPropertyProject> findByTagsLikeOrderByFavoriteNumberDesc(String keyword,Pageable pageable);

    @Query(value = "select * from lost_property_project order by update_time*0.000001+favorite_number*10 desc limit ?1 offset ?2",
            nativeQuery = true)
    List<LostPropertyProject> findByComplex(Integer pageSize, Integer offsetNumber);
}
