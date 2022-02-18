package com.imooc.sell.repository;

import com.imooc.sell.dataobject.IdleProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.util.List;


public interface IdleProjectRepository extends JpaRepository<IdleProject, Integer> {
    IdleProject findByProjectId(String projectId);
    Page<IdleProject> findByOrderByUpdateTimeDesc(Pageable pageable);
    Page<IdleProject> findByOrderByAmount(Pageable pageable);
    Page<IdleProject> findByOrderByAmountDesc(Pageable pageable);
    List<IdleProject> findByTitle(String title);
    List<IdleProject> findByLocation(String location);
    //调用者要在关键字前后加上“%”，以实现模糊查询
    Page<IdleProject> findByTagsLike(String keyword,Pageable pageable);
}
