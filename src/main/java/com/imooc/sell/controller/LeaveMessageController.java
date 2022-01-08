package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.controller.form.LectureProjectFrom;
import com.imooc.sell.converter.LecutreProjectFrom2LectureProjectDTOConverter;
import com.imooc.sell.dto.LeaveMessageDTO;
import com.imooc.sell.dto.LectureProjectDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.impl.LeaveMessageServiceImpl;
import com.imooc.sell.utils.ResultVOUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/leaveMessage")
@Slf4j
@Api(tags = "留言信息-访问接口")
public class LeaveMessageController {
    @Autowired
    LeaveMessageServiceImpl leaveMessageService;

    @ApiOperation(value = "创建留言", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId",value = "项目Id",required=true),
            @ApiImplicitParam(name = "openid",value = "用户Id",required=true),
            @ApiImplicitParam(name = "content",value = "留言内容",required=true)
    })
    @PostMapping("/create")
    public ResultVO create(@RequestParam(value = "openid") String openid,
                           @RequestParam(value = "projectId") String projectId,
                           @RequestParam(value = "content") String content
                           ) throws Exception {
        if (openid == null || projectId == null || content == null){
            log.error("【创建留言】参数错误， userOpenId = {}, projectId = {}, content = {}",openid,projectId,content);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        LeaveMessageDTO leaveMessageDTO = leaveMessageService.createOne(openid,projectId,content);
        if (leaveMessageDTO == null){
            return ResultVOUtil.error(ResultEnum.CREATE_FAILED);
        }
        return ResultVOUtil.success(leaveMessageDTO);
    }

    @ApiOperation(value = "查询一个用户的所有留言", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "userOpenId",value = "用户Id",required=true)
    @PostMapping("/find/userOpenId")
    public ResultVO findByUserOpenId(@RequestParam(value = "openid") String openid){
        if (openid == null){
            log.error("【搜索留言】参数错误， userOpenId = {}", (Object) null);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        List<LeaveMessageDTO>leaveMessageDTOS = leaveMessageService.findByOpenId(openid);
        return ResultVOUtil.success(leaveMessageDTOS);
    }

    @ApiOperation(value = "查询一个项目下的所有留言", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "projectId",value = "项目Id",required=true)
    @PostMapping("/find/projectId")
    public ResultVO findByProjectId(@RequestParam(value = "projectId") String projectId){
        if (projectId == null){
            log.error("【搜索留言】参数错误， projectId = {}", (Object) null);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        List<LeaveMessageDTO>leaveMessageDTOS = leaveMessageService.findByProjectId(projectId);
        return ResultVOUtil.success(leaveMessageDTOS);
    }

    @ApiOperation(value = "删除留言", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId",value = "项目Id",required=true),
            @ApiImplicitParam(name = "openid",value = "用户Id",required=true),
            @ApiImplicitParam(name = "id",value = "留言Id",required=true)
    })
    @PostMapping("/delete")
    public ResultVO delete(@RequestParam(value = "openid") String openid,
                           @RequestParam(value = "projectId") String projectId,
                           @RequestParam(value = "id") Integer id){
        if (openid == null || projectId == null){
            log.error("【删除留言】参数错误， userOpenId = {}, projectId = {}",openid,projectId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        boolean deleteResult = leaveMessageService.deleteOne(openid,projectId,id);
        return ResultVOUtil.success(deleteResult);
    }

    @ApiOperation(value = "修改留言", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId",value = "项目Id",required=true),
            @ApiImplicitParam(name = "openid",value = "用户Id",required=true),
            @ApiImplicitParam(name = "id",value = "留言Id",required=true),
            @ApiImplicitParam(name = "content",value = "留言内容",required=true)
    })
    @PostMapping("/change")
    public ResultVO change(@RequestParam(value = "openid") String openid,
                           @RequestParam(value = "projectId") String projectId,
                           @RequestParam(value = "content") String content,
                           @RequestParam(value = "id") Integer id
    ) throws Exception{
        if (openid == null || projectId == null || content == null){
            log.error("【修改留言】参数错误， userOpenId = {}, projectId = {}, content = {}",openid,projectId,content);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        LeaveMessageDTO leaveMessageDTO = leaveMessageService.changeOne(openid,projectId,content,id);
        if (leaveMessageDTO == null){
            return ResultVOUtil.error(ResultEnum.CHANGE_FAILED);
        }
        return ResultVOUtil.success(leaveMessageDTO);
    }
}