package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.controller.form.RewardProjectFrom;
import com.imooc.sell.controller.form.StudyProjectFrom;
import com.imooc.sell.converter.RewardProjectFrom2RewardDTOConverter;
import com.imooc.sell.converter.StudyProjectFrom2StudyDTOConverter;
import com.imooc.sell.dto.RewardProjectDTO;
import com.imooc.sell.dto.StudyProjectDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.StudyProjectService;
import com.imooc.sell.service.impl.ProjectMasterServiceImpl;
import com.imooc.sell.service.impl.RewardServiceImpl;
import com.imooc.sell.service.impl.StudyServiceImpl;
import com.imooc.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/project/study")
@Slf4j

public class StudyProjectController {
    @Autowired
    ProjectMasterServiceImpl projectMasterService;
    @Autowired
    StudyServiceImpl studyService;

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid StudyProjectFrom studyProjectFrom,
                                                BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.error("【创建学习项目】参数不正确, studyProjectFrom={}", studyProjectFrom);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        StudyProjectDTO studyProjectDTO = StudyProjectFrom2StudyDTOConverter.convert(studyProjectFrom);

        StudyProjectDTO registerResult = studyService.createStudyProject(studyProjectDTO);

        return ResultVOUtil.success(registerResult);

    }

    @PostMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        PageRequest pageRequest = new PageRequest(page, size);
        List<StudyProjectDTO> list = studyService.findStudyProjectsOrderByUpdateTime(pageRequest);
        return ResultVOUtil.success(list);

    }
    @PostMapping("/findOne")
    public ResultVO findOne(@RequestParam(value = "projectId") String projectId){
        return  ResultVOUtil.success(studyService.findStudyByProjectId(projectId));
    }

    @PostMapping("/deleteOne")
    public ResultVO deleteOne(@RequestParam(value = "projectId") String projectId,
                              @RequestParam(value = "openid") String openid){
        return  ResultVOUtil.success(projectMasterService.deleateProjectMaster(projectId,openid));
    }
}
