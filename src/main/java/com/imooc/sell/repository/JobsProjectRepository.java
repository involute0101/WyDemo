package com.imooc.sell.repository;

import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dataobject.JobsProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface JobsProjectRepository extends JpaRepository<JobsProject, Integer> {
    JobsProject findByProjectId(String projectId);

    Page<JobsProject> findByOrderByUpdateTimeDesc(Pageable pageable);

    Page<JobsProject> findByTagsLikeOrderByUpdateTimeDesc(String keyword,Pageable pageable);

    Page<JobsProject> findByOrderByFavoriteNumberDesc(Pageable pageable);

    Page<JobsProject> findByOrderByAmount(Pageable pageable);

    Page<JobsProject> findByOrderByAmountDesc(Pageable pageable);

    List<JobsProject> findByTitle(String title);

    Page<JobsProject> findByTitleLike(String keyword, Pageable pageable);

    List<JobsProject> findByLocation(String location);

    //调用者要在关键字前后加上“%”，以实现模糊查询
    Page<JobsProject> findByTagsLike(String keyword, Pageable pageable);

    Page<JobsProject> findByTagsLikeOrderByFavoriteNumberDesc(String keyword,Pageable pageable);

    @Query(value = "select * from jobs_roject order by update_time*0.000001+favorite_number*10 desc limit ?1 offset ?2",
            nativeQuery = true)
    List<JobsProject> findByComplex(Integer pageSize, Integer offsetNumber);

    List<JobsProject> findByUserOpenId(String userOpenId);
}
