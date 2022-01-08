package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.service.impl.ProjectMasterServiceImpl;
import com.imooc.sell.utils.ResultVOUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/project/master")
@Slf4j
@Api(tags = "项目主表-访问接口")
public class ProjectMasterController {
    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @ApiOperation(value = "查找用户对应类型的所有项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectType",value = "项目类型",required=true),
            @ApiImplicitParam(name = "openid",value = "用户Id",required=true),
    })
    @PostMapping("/list/userOpenidAndType")
    public ResultVO list(@RequestParam(value = "openid") String openid,
                         @RequestParam(value = "projectType") Integer projectType) throws Exception {
        return ResultVOUtil.success(projectMasterService.findProjectMasterByUserOpenIdAndProjectType(openid,projectType));

    }
}
