package com.imooc.sell.repository;

import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dataobject.PurchasingProject;
import com.imooc.sell.dataobject.RewardProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PurchasingProjectRepository extends JpaRepository<PurchasingProject, Integer> {
    PurchasingProject findByProjectId(String projectId);

    Page<PurchasingProject> findByOrderByUpdateTimeDesc(Pageable pageable);

    Page<PurchasingProject> findByTagsLikeOrderByUpdateTimeDesc(String keyword,Pageable pageable);

    Page<PurchasingProject> findByOrderByAmount(Pageable pageable);

    Page<PurchasingProject> findByOrderByAmountDesc(Pageable pageable);

    List<PurchasingProject> findByTitle(String title);

    List<PurchasingProject> findByLocation(String location);

    //调用者要在关键字前后加上“%”，以实现模糊查询
    Page<PurchasingProject> findByTagsLike(String keyword, Pageable pageable);

    Page<PurchasingProject> findByTitleLike(String titleKeyword, Pageable pageable);

    Page<PurchasingProject> findByOrderByFavoriteNumberDesc(Pageable pageable);

    Page<PurchasingProject> findByTagsLikeOrderByFavoriteNumberDesc(String keyword,Pageable pageable);

    @Query(value = "select * from purchasing_project order by update_time*0.000001+favorite_number*10 desc limit ?1 offset ?2",
            nativeQuery = true)
    List<PurchasingProject> findByComplex(Integer pageSize, Integer offsetNumber);

    List<PurchasingProject> findByUserOpenId(String userOpenId);
}
