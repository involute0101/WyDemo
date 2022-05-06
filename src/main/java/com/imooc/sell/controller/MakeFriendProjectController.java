package com.imooc.sell.controller;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.controller.form.MakeFriendProjectForm;
import com.imooc.sell.converter.MakeFriendProjectFrom2MakeFriendDTOConverter;
import com.imooc.sell.dto.MakeFriendProjectDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.impl.LeaveMessageServiceImpl;
import com.imooc.sell.service.impl.MakeFriendProjectServiceImpl;
import com.imooc.sell.service.impl.UserInfoServiceImpl;
import com.imooc.sell.utils.ResultVOUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/project/makeFriend")
@Slf4j
@Api(tags = "交友项目-访问接口")
public class MakeFriendProjectController {
    @Autowired
    MakeFriendProjectServiceImpl makeFriendProjectService;

    @Autowired
    UserInfoServiceImpl userInfoService;

    @Autowired
    LeaveMessageServiceImpl leaveMessageService;

    @ApiOperation(value = "创建交友项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("/create")
    public ResultVO create(@Valid MakeFriendProjectForm makeFriendProjectForm,
                           BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            log.error("【创建交友项目】参数不正确, makeFriendProjectForm={}", makeFriendProjectForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        makeFriendProjectService.tagHandler(makeFriendProjectForm);
        MakeFriendProjectDTO makeFriendProjectDTO = MakeFriendProjectFrom2MakeFriendDTOConverter.convert(makeFriendProjectForm);
        MakeFriendProjectDTO result = makeFriendProjectService.createMakeFriendProject(makeFriendProjectDTO);
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "按照更新时间查询交友项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
    })
    @PostMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        if (page<=0)return ResultVOUtil.error(403,"请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page-1, size);
        List<JSONObject> list = makeFriendProjectService.findMakeFriendProjectsOrderByUpdateTime(pageRequest);
        return ResultVOUtil.success(list);
    }

    @ApiOperation(value = "按照 热度 查询交友项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
    })
    @PostMapping("/popular")
    public ResultVO findMakeFriendProjectOrderByFavoritesNumber(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        if (page <= 0) return ResultVOUtil.error(403, "请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page - 1, size);
        List<MakeFriendProjectDTO> list = makeFriendProjectService.findMakeFriendProjectOrderByFavoritesNumber(pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(MakeFriendProjectDTO makeFriendProjectDTO : list){
            JSONObject makeFriendProjectInfo = JSONObject.parseObject(makeFriendProjectDTO.toString());
            UserInfoDTO userInfo = userInfoService.findUserInfoByUserOpeinid(makeFriendProjectDTO.getUserOpenId());
            makeFriendProjectInfo.put("headPortrait",userInfo.getHeadPortrait());
            makeFriendProjectInfo.put("userName",userInfo.getUserName());
            makeFriendProjectInfo.put("major",userInfo.getUserMajor());
            makeFriendProjectInfo.put("university",userInfo.getUserUniversity());
            makeFriendProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(makeFriendProjectDTO.getProjectId()));
            result.add(makeFriendProjectInfo);
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
    public ResultVO findMakeFriendProjectComplex(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        if (page <= 0) return ResultVOUtil.error(403, "请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page - 1, size);
        List<MakeFriendProjectDTO> list = makeFriendProjectService.findByComplexService(pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(MakeFriendProjectDTO makeFriendProjectDTO : list){
            JSONObject makeFriendProjectInfo = JSONObject.parseObject(makeFriendProjectDTO.toString());
            UserInfoDTO userInfo = userInfoService.findUserInfoByUserOpeinid(makeFriendProjectDTO.getUserOpenId());
            makeFriendProjectInfo.put("headPortrait",userInfo.getHeadPortrait());
            makeFriendProjectInfo.put("userName",userInfo.getUserName());
            makeFriendProjectInfo.put("major",userInfo.getUserMajor());
            makeFriendProjectInfo.put("university",userInfo.getUserUniversity());
            makeFriendProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(makeFriendProjectDTO.getProjectId()));
            result.add(makeFriendProjectInfo);
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
        List<MakeFriendProjectDTO> list = makeFriendProjectService.findByTitleLike(titleKeyword, pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(MakeFriendProjectDTO makeFriendProjectDTO : list){
            JSONObject makeFriendProjectInfo = JSONObject.parseObject(makeFriendProjectDTO.toString());
            UserInfoDTO userInfo = userInfoService.findUserInfoByUserOpeinid(makeFriendProjectDTO.getUserOpenId());
            makeFriendProjectInfo.put("headPortrait",userInfo.getHeadPortrait());
            makeFriendProjectInfo.put("userName",userInfo.getUserName());
            makeFriendProjectInfo.put("major",userInfo.getUserMajor());
            makeFriendProjectInfo.put("university",userInfo.getUserUniversity());
            makeFriendProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(makeFriendProjectDTO.getProjectId()));
            result.add(makeFriendProjectInfo);
        }
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "按照项目id查找项目详情", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "projectId",value = "项目id",required=true)
    @PostMapping("/findOne")
    public ResultVO findOne(@RequestParam(value = "projectId") String projectId){
        MakeFriendProjectDTO makeFriendProjectDTO = makeFriendProjectService.findRewardByProjectId(projectId);
        JSONObject makeFriendProjectInfo = JSONObject.parseObject(makeFriendProjectDTO.toString());
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(makeFriendProjectDTO.getUserOpenId());
        makeFriendProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
        makeFriendProjectInfo.put("userName",userInfoDTO.getUserName());
        makeFriendProjectInfo.put("major",userInfoDTO.getUserMajor());
        makeFriendProjectInfo.put("university",userInfoDTO.getUserUniversity());
        makeFriendProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(makeFriendProjectDTO.getProjectId()));
        return ResultVOUtil.success(makeFriendProjectInfo);
    }

    @ApiOperation(value = "增加浏览量", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "projectId",value = "项目id",required=true)
    @PostMapping("/pageviews")
    public ResultVO incPageviews(@RequestParam(value = "projectId") String projectId){
        MakeFriendProjectDTO makeFriendProjectDTO = makeFriendProjectService.increasePageviews(projectId);
        JSONObject makeFriendProjectInfo = JSONObject.parseObject(makeFriendProjectDTO.toString());
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(makeFriendProjectDTO.getUserOpenId());
        makeFriendProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
        makeFriendProjectInfo.put("userName",userInfoDTO.getUserName());
        makeFriendProjectInfo.put("major",userInfoDTO.getUserMajor());
        makeFriendProjectInfo.put("university",userInfoDTO.getUserUniversity());
        makeFriendProjectInfo.put("leaveMessage",leaveMessageService.getMessageCountOfProject(makeFriendProjectDTO.getProjectId()));
        return ResultVOUtil.success(makeFriendProjectInfo);
    }
}
