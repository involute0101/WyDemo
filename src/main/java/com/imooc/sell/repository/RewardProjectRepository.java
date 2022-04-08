package com.imooc.sell.repository;

import com.imooc.sell.dataobject.PurchasingProject;
import com.imooc.sell.dataobject.RewardProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RewardProjectRepository extends JpaRepository<RewardProject, Integer> {
    RewardProject findByProjectId(String projectId);

    Page<RewardProject> findByOrderByUpdateTimeDesc(Pageable pageable);

    Page<RewardProject> findByOrderByAmount(Pageable pageable);

    Page<RewardProject> findByOrderByAmountDesc(Pageable pageable);

    List<RewardProject> findByTitle(String title);

    List<RewardProject> findByLocation(String location);

    //调用者要在关键字前后加上“%”，以实现模糊查询
    Page<RewardProject> findByTagsLike(String keyword, Pageable pageable);



    Page<RewardProject> findByTagsLikeOrderByUpdateTimeDesc(String keyword,Pageable pageable);

    Page<RewardProject> findByTitleLike(String keyword, Pageable pageable);

    Page<RewardProject> findByOrderByFavoriteNumberDesc(Pageable pageable);

    Page<RewardProject> findByTagsLikeOrderByFavoriteNumberDesc(String keyword,Pageable pageable);

    @Query(value = "select * from reward_project order by update_time*0.000001+favorite_number*10 desc limit ?1 offset ?2",
            nativeQuery = true)
    List<RewardProject> findByComplex(Integer pageSize, Integer offsetNumber);
}
