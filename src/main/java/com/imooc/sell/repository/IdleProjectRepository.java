package com.imooc.sell.repository;

import com.imooc.sell.dataobject.IdleProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.util.List;


public interface IdleProjectRepository extends JpaRepository<IdleProject, Integer> {
    IdleProject findByProjectId(String projectId);

    Page<IdleProject> findByOrderByUpdateTimeDesc(Pageable pageable);

    Page<IdleProject> findByTagsLikeOrderByUpdateTimeDesc(String keyword,Pageable pageable);

    Page<IdleProject> findByOrderByAmount(Pageable pageable);

    Page<IdleProject> findByOrderByAmountDesc(Pageable pageable);

    List<IdleProject> findByTitle(String title);

    List<IdleProject> findByLocation(String location);

    //调用者要在关键字前后加上“%”，以实现模糊查询
    Page<IdleProject> findByTagsLike(String keyword, Pageable pageable);

    Page<IdleProject> findByTitleLike(String keyword, Pageable pageable);

    Page<IdleProject> findByTagsLikeOrderByFavoriteNumberDesc(String keyword,Pageable pageable);

    Page<IdleProject> findByOrderByFavoriteNumberDesc(Pageable pageable);

    @Query(value = "select * from idle_project order by update_time*0.000001+favorite_number*10 desc limit ?1 offset ?2",
            nativeQuery = true)
    List<IdleProject> findByComplex(Integer pageSize, Integer offsetNumber);

    List<IdleProject> findByUserOpenId(String userOpenId);
}
