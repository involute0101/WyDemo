package com.imooc.sell.repository;

import com.imooc.sell.dataobject.JobsProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface JobsProjectRepository extends JpaRepository<JobsProject, Integer> {
    JobsProject findByProjectId(String projectId);

    Page<JobsProject> findByOrderByUpdateTimeDesc(Pageable pageable);

    Page<JobsProject> findByTagsLikeOrderByUpdateTimeDesc(String keyword,Pageable pageable);

    Page<JobsProject> findByOrderByFavoriteNumberDesc(Pageable pageable);

    List<JobsProject> findByTitle(String title);

    List<JobsProject> findByLocation(String location);

    //调用者要在关键字前后加上“%”，以实现模糊查询
    Page<JobsProject> findByTagsLike(String keyword, Pageable pageable);

    Page<JobsProject> findByTagsLikeOrderByFavoriteNumberDesc(String keyword,Pageable pageable);
}
