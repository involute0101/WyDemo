package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.controller.form.JobsProjectFrom;
import com.imooc.sell.converter.JobsProjectFrom2JobsProjectProjectDTOConverter;
import com.imooc.sell.dto.JobsProjectDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.impl.JobsProjectServiceImpl;
import com.imooc.sell.service.impl.ProjectMasterServiceImpl;
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
@RequestMapping("/project/jobs")
@Slf4j

public class JobsProjectController {
    @Autowired
    ProjectMasterServiceImpl projectMasterService;
    @Autowired
    JobsProjectServiceImpl jobsProjectService;

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid JobsProjectFrom jobsProjectFrom,
                                                BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.error("【创建项目】参数不正确, jobsProjectFrom={}", jobsProjectFrom);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        JobsProjectDTO jobsProjectDTO = JobsProjectFrom2JobsProjectProjectDTOConverter.convert(jobsProjectFrom);

        JobsProjectDTO registerResult = jobsProjectService.createJobsProjectOne(jobsProjectDTO);

        return ResultVOUtil.success(registerResult);

    }

    @PostMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        PageRequest pageRequest = new PageRequest(page, size);
        List<JobsProjectDTO> list = jobsProjectService.findJobsProjectsOrderByUpdateTime(pageRequest);
        return ResultVOUtil.success(list);

    }
    @PostMapping("/findOne")
    public ResultVO findOne(@RequestParam(value = "projectId") String projectId){
        return  ResultVOUtil.success(jobsProjectService.findJobsProjectByProjectId(projectId));
    }

    @PostMapping("/deleteOne")
    public ResultVO deleteOne(@RequestParam(value = "projectId") String projectId,
                              @RequestParam(value = "openid") String openid){
        return  ResultVOUtil.success(projectMasterService.deleateProjectMaster(projectId,openid));
    }
}
