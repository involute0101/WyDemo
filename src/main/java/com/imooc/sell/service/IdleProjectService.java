package com.imooc.sell.service;

import com.imooc.sell.dataobject.IdleProject;
import com.imooc.sell.dto.IdleProjectDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IdleProjectService {
    IdleProjectDTO createIdleProjectOne(IdleProjectDTO idleProjectDTO) throws Exception;
    List<IdleProjectDTO> findIdleProjectsOrderByUpdateTime(Pageable pageable);
    IdleProjectDTO findIdleProjectByProjectId(String projectId);
}
