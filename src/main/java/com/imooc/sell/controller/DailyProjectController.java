package com.imooc.sell.controller;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.controller.form.DailyProjectForm;
import com.imooc.sell.converter.DailyProjectFrom2DailyDTOConverter;
import com.imooc.sell.dataobject.DailyProject;
import com.imooc.sell.dto.DailyProjectDTO;
import com.imooc.sell.dto.IdleProjectDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.impl.DailyProjectServiceImpl;
import com.imooc.sell.service.impl.LeaveMessageServiceImpl;
import com.imooc.sell.service.impl.UserInfoServiceImpl;
import com.imooc.sell.utils.ResultVOUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/project/daily")
@Slf4j
@Api(tags = "日常项目-访问接口")
public class DailyProjectController {
    @Autowired
    DailyProjectServiceImpl dailyProjectService;

    @Autowired
    UserInfoServiceImpl userInfoServicel;

    @Autowired
    LeaveMessageServiceImpl leaveMessageService;

    @ApiOperation(value = "创建日常项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("/create")
    public ResultVO create(@Valid DailyProjectForm dailyProjectForm,
                           BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            log.error("【创建日常项目】参数不正确, dailyProjectForm={}", dailyProjectForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        dailyProjectService.tagHandler(dailyProjectForm);
        DailyProjectDTO dailyProjectDTO = DailyProjectFrom2DailyDTOConverter.convert(dailyProjectForm);
        DailyProjectDTO result = dailyProjectService.createDailyProject(dailyProjectDTO);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "按照更新时间查询 日常项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
    })
    @PostMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        if (page <= 0) return ResultVOUtil.error(403, "请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page - 1, size);
        List<DailyProjectDTO> list = dailyProjectService.findDailyProjectsOrderByUpdateTime(pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(DailyProjectDTO dailyProjectDTO : list){
            JSONObject dailyProjectInfo = JSONObject.parseObject(dailyProjectDTO.toString());
            UserInfoDTO userInfoDTO = userInfoServicel.findUserInfoByUserOpenId(dailyProjectDTO.getUserOpenId());
            dailyProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
            dailyProjectInfo.put("userName",userInfoDTO.getUserName());
            dailyProjectInfo.put("major",userInfoDTO.getUserMajor());
            dailyProjectInfo.put("university",userInfoDTO.getUserUniversity());
            dailyProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(dailyProjectDTO.getProjectId()));
            result.add(dailyProjectInfo);
        }
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "按照 热度 查询日常项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
    })
    @PostMapping("/popular")
    public ResultVO findDailyProjectOrderByFavoritesNumber(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        if (page <= 0) return ResultVOUtil.error(403, "请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page - 1, size);
        List<DailyProjectDTO> list = dailyProjectService.findDailyProjectOrderByFavoritesNumber(pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(DailyProjectDTO dailyProjectDTO : list){
            JSONObject dailyProjectInfo = JSONObject.parseObject(dailyProjectDTO.toString());
            UserInfoDTO userInfoDTO = userInfoServicel.findUserInfoByUserOpenId(dailyProjectDTO.getUserOpenId());
            dailyProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
            dailyProjectInfo.put("userName",userInfoDTO.getUserName());
            dailyProjectInfo.put("major",userInfoDTO.getUserMajor());
            dailyProjectInfo.put("university",userInfoDTO.getUserUniversity());
            dailyProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(dailyProjectDTO.getProjectId()));
            result.add(dailyProjectInfo);
        }
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "综合排序", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
    })
    @PostMapping("/complex")
    public ResultVO findDailyProjectComplex(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        if (page <= 0) return ResultVOUtil.error(403, "请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page - 1, size);
        List<DailyProjectDTO> list = dailyProjectService.findByComplexService(pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(DailyProjectDTO dailyProjectDTO : list){
            JSONObject dailyProjectInfo = JSONObject.parseObject(dailyProjectDTO.toString());
            UserInfoDTO userInfoDTO = userInfoServicel.findUserInfoByUserOpenId(dailyProjectDTO.getUserOpenId());
            dailyProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
            dailyProjectInfo.put("userName",userInfoDTO.getUserName());
            dailyProjectInfo.put("major",userInfoDTO.getUserMajor());
            dailyProjectInfo.put("university",userInfoDTO.getUserUniversity());
            dailyProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(dailyProjectDTO.getProjectId()));
            result.add(dailyProjectInfo);
        }
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "根据标题关键字搜索", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
            @ApiImplicitParam(name = "titleKeyword",value = "标题（的关键字）",required=true),
    })
    @PostMapping("/titleLike")
    public ResultVO findByTitleLike(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestParam(value = "titleKeyword", defaultValue = "") String titleKeyword) {
        if (page <= 0) return ResultVOUtil.error(403, "请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page - 1, size);
        List<DailyProjectDTO> list = dailyProjectService.findByTitleLike(titleKeyword, pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(DailyProjectDTO dailyProjectDTO : list){
            JSONObject dailyProjectInfo = JSONObject.parseObject(dailyProjectDTO.toString());
            UserInfoDTO userInfoDTO = userInfoServicel.findUserInfoByUserOpenId(dailyProjectDTO.getUserOpenId());
            dailyProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
            dailyProjectInfo.put("userName",userInfoDTO.getUserName());
            dailyProjectInfo.put("major",userInfoDTO.getUserMajor());
            dailyProjectInfo.put("university",userInfoDTO.getUserUniversity());
            dailyProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(dailyProjectDTO.getProjectId()));
            result.add(dailyProjectInfo);
        }
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "按照项目id查找项目详情", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "projectId",value = "项目id",required=true)
    @PostMapping("/findOne")
    public ResultVO findOne(@RequestParam(value = "projectId") String projectId){
        DailyProjectDTO dailyProjectDTO = dailyProjectService.findRewardByProjectId(projectId);
        JSONObject dailyProjectInfo = JSONObject.parseObject(dailyProjectDTO.toString());
        UserInfoDTO userInfoDTO = userInfoServicel.findUserInfoByUserOpenId(dailyProjectDTO.getUserOpenId());
        dailyProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
        dailyProjectInfo.put("userName",userInfoDTO.getUserName());
        dailyProjectInfo.put("major",userInfoDTO.getUserMajor());
        dailyProjectInfo.put("university",userInfoDTO.getUserUniversity());
        dailyProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(dailyProjectDTO.getProjectId()));
        return ResultVOUtil.success(dailyProjectInfo);
    }

    @ApiOperation(value = "增加浏览量", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "projectId",value = "项目id",required=true)
    @PostMapping("/pageviews")
    public ResultVO incPageviews(@RequestParam(value = "projectId") String projectId){
        DailyProjectDTO dailyProjectDTO = dailyProjectService.increasePageviews(projectId);
        JSONObject dailyProjectInfo = JSONObject.parseObject(dailyProjectDTO.toString());
        UserInfoDTO userInfoDTO = userInfoServicel.findUserInfoByUserOpenId(dailyProjectDTO.getUserOpenId());
        dailyProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
        dailyProjectInfo.put("userName",userInfoDTO.getUserName());
        dailyProjectInfo.put("major",userInfoDTO.getUserMajor());
        dailyProjectInfo.put("university",userInfoDTO.getUserUniversity());
        dailyProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(dailyProjectDTO.getProjectId()));
        return ResultVOUtil.success(dailyProjectInfo);
    }
}
