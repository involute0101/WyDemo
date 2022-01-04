package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.service.impl.ProjectMasterServiceImpl;
import com.imooc.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/project/master")
@Slf4j
public class ProjectMasterController {
    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @PostMapping("/list/userOpenidAndType")
    public ResultVO list(@RequestParam(value = "openid") String openid,
                         @RequestParam(value = "projectType") Integer projectType) throws Exception {
        return ResultVOUtil.success(projectMasterService.findProjectMasterByUserOpenIdAndProjectType(openid,projectType));

    }
}
