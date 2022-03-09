package com.imooc.sell.repository;

import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dataobject.LostPropertyProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LostPropertyProjectRepository extends JpaRepository<LostPropertyProject, Integer> {
    LostPropertyProject findByProjectId(String projectId);

    Page<LostPropertyProject> findByOrderByUpdateTimeDesc(Pageable pageable);

    Page<LostPropertyProject> findByTagsLikeOrderByUpdateTimeDesc(String keyword,Pageable pageable);

    List<LostPropertyProject> findByTitle(String title);

    List<LostPropertyProject> findByLocation(String location);

    //调用者要在关键字前后加上“%”，以实现模糊查询
    Page<LostPropertyProject> findByTagsLike(String keyword, Pageable pageable);

    Page<LostPropertyProject> findByTagsLikeOrderByFavoriteNumberDesc(String keyword,Pageable pageable);
}
