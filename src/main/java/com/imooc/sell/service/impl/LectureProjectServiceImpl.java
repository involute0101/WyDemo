package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.LectureProject;
import com.imooc.sell.dto.LectureProjectDTO;
import com.imooc.sell.dto.ProjectMasterDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.LectureProjectRepository;
import com.imooc.sell.service.LectureProjectService;
import com.imooc.sell.service.UserInfoService;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LectureProjectServiceImpl implements LectureProjectService {
    @Autowired
    LectureProjectRepository lectureProjectRepository;

    @Autowired
    UserInfoService userInfoService ;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Override
    @Transactional
    public LectureProjectDTO createLectureProjectOne(LectureProjectDTO lectureProjectDTO) throws Exception {
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(lectureProjectDTO.getUserOpenid());
        if (userInfoDTO == null){
            throw  new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Integer userId = userInfoDTO.getUserId();
        ProjectMasterDTO projectMasterDTO = new ProjectMasterDTO();
        projectMasterDTO.setProjectType(0);
        projectMasterDTO.setProjectId(KeyUtil.genUniqueKey());
        projectMasterDTO.setUserId(userId);
        ProjectMasterDTO createResult = projectMasterService.createProjectMasterOne(projectMasterDTO);
        if (createResult == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        lectureProjectDTO.setProjectId(projectMasterDTO.getProjectId());
        LectureProject lectureProject = new LectureProject();
        BeanUtils.copyProperties(lectureProjectDTO,lectureProject);
        LectureProject result = lectureProjectRepository.save(lectureProject);
        LectureProjectDTO resultDTO = new LectureProjectDTO();
        BeanUtils.copyProperties(result, resultDTO);
        return resultDTO;
    }

    @Override
    public List<LectureProjectDTO> findLectureProjectsOrderByUpdateTime(Pageable pageable) {
        Page<LectureProject> page = lectureProjectRepository.findByOrderByUpdateTimeDesc(pageable);
        List<LectureProjectDTO> list = new ArrayList<>();

        for (LectureProject lectureProject: page){
            LectureProjectDTO lectureProjectDTO = new LectureProjectDTO();
            BeanUtils.copyProperties(lectureProject, lectureProjectDTO);
            list.add(lectureProjectDTO);
        }
        return list;
    }


    @Override
    public LectureProjectDTO findLectureProjectByProjectId(String projectId) {
        LectureProject lectureProject = lectureProjectRepository.findByProjectId(projectId);
        LectureProjectDTO lectureProjectDTO = new LectureProjectDTO();
        if(lectureProject != null) {
            BeanUtils.copyProperties(lectureProject, lectureProjectDTO);
            return lectureProjectDTO;
        }
        else {
            throw new SellException(ResultEnum.PROJECT_MASTER_NOT_FOUND_BY_PROJECT_ID);
        }
    }
}
