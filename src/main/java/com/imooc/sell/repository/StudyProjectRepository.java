package com.imooc.sell.repository;

import com.imooc.sell.dataobject.LostPropertyProject;
import com.imooc.sell.dataobject.RewardProject;
import com.imooc.sell.dataobject.StudyProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StudyProjectRepository extends JpaRepository<StudyProject, Integer> {
    StudyProject findByProjectId(String projectId);

    Page<StudyProject> findByOrderByUpdateTimeDesc(Pageable pageable);

    Page<StudyProject> findByTagsLikeOrderByUpdateTimeDesc(String keyword,Pageable pageable);

    Page<StudyProject> findByOrderByFavoriteNumberDesc(Pageable pageable);

    Page<StudyProject> findByOrderByAmount(Pageable pageable);

    Page<StudyProject> findByOrderByAmountDesc(Pageable pageable);

    List<StudyProject> findByTitle(String title);

    Page<StudyProject> findByTitleLike(String keyword, Pageable pageable);

    List<StudyProject> findByLocation(String location);

    //调用者要在关键字前后加上“%”，以实现模糊查询
    Page<StudyProject> findByTagsLike(String keyword, Pageable pageable);

    Page<StudyProject> findByTagsLikeOrderByFavoriteNumberDesc(String keyword,Pageable pageable);

    @Query(value = "select * from study_project order by update_time*0.000001+favorite_number*10 desc limit ?1 offset ?2",
            nativeQuery = true)
    List<StudyProject> findByComplex(Integer pageSize, Integer offsetNumber);
}
