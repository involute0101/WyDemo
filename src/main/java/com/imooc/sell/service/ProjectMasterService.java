package com.imooc.sell.service;

import com.imooc.sell.dto.ProjectMasterDTO;

import java.util.List;

public interface ProjectMasterService {
    ProjectMasterDTO createProjectMasterOne(ProjectMasterDTO projectMasterDTO) throws Exception;

    List<ProjectMasterDTO> findProjectMasterByUserOpenId(String userOpenId);

    List<ProjectMasterDTO> findProjectMasterByUserOpenIdAndProjectType(String userOpenId, Integer projectType);

    ProjectMasterDTO findProjectMasterByProjectId(String projectId);

    ProjectMasterDTO setProjectMasterOnProjectType(String projectId, String userOpenId, Integer projectType);

    Boolean deleateProjectMaster(String projectId, String userOpenId);
}
