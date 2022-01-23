package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.controller.form.IdleProjectFrom;
import com.imooc.sell.controller.form.UserInfoFrom;
import com.imooc.sell.converter.IdleProjectFrom2IdleProjectDTOConverter;
import com.imooc.sell.converter.UserInfoFrom2UserInfoDTOConverter;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.ProjectMasterService;
import com.imooc.sell.service.impl.IdleProjectServiceImpl;
import com.imooc.sell.service.impl.JobsProjectServiceImpl;
import com.imooc.sell.service.impl.ProjectMasterServiceImpl;
import com.imooc.sell.utils.ResultVOUtil;
import io.swagger.annotations.*;
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
@RequestMapping("/project/idle")
@Slf4j
@Api(tags = "闲置项目-访问接口")
public class IdleProjectController {

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @Autowired
    IdleProjectServiceImpl idleProjectService;

    @ApiOperation(value = "创建闲置项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid IdleProjectFrom idleProjectFrom,
                                                BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.error("【创建项目】参数不正确, idleProjectFrom={}", idleProjectFrom);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        IdleProjectDTO idleProjectDTO = IdleProjectFrom2IdleProjectDTOConverter.convert(idleProjectFrom);

        IdleProjectDTO registerResult = idleProjectService.createIdleProjectOne(idleProjectDTO);

        return ResultVOUtil.success(registerResult);

    }

    @ApiOperation(value = "按照更新时间查询闲置项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
    })
    @PostMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        if (page<=0)return ResultVOUtil.error(403,"请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page-1, size);
        List<IdleProjectDTO> list = idleProjectService.findIdleProjectsOrderByUpdateTime(pageRequest);
        return ResultVOUtil.success(list);

    }

    @ApiOperation(value = "按照项目id查找项目详情", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "projectId",value = "项目id",required=true)
    @PostMapping("/findOne")
    public ResultVO findOne(@RequestParam(value = "projectId") String projectId){
        return  ResultVOUtil.success(idleProjectService.findIdleProjectByProjectId(projectId));
    }

    @ApiOperation(value = "删除项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId",value = "项目Id",required=true),
            @ApiImplicitParam(name = "openid",value = "用户Id",required=true),
    })
    @PostMapping("/deleteOne")
    public ResultVO deleteOne(@RequestParam(value = "projectId") String projectId,
                              @RequestParam(value = "openid") String openid){
        return  ResultVOUtil.success(projectMasterService.deleateProjectMaster(projectId,openid));
    }
}
