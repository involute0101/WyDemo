package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dto.FavoritesDTO;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.impl.FavoritesServiceServiceImpl;
import com.imooc.sell.service.impl.ProjectMasterServiceImpl;
import com.imooc.sell.service.impl.UserInfoServiceImpl;
import com.imooc.sell.utils.ResultVOUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/project/favorites")
@Slf4j
@Api(tags = "收藏项目-访问接口")
public class FavoritesController {
    @Autowired
    FavoritesServiceServiceImpl favoritesServiceService;

    @Autowired
    UserInfoServiceImpl userInfoService;

    @Autowired
    ProjectMasterServiceImpl projectMasterService;

    @ApiOperation(value = "创建收藏项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId",value = "项目Id",required=true),
            @ApiImplicitParam(name = "openid",value = "用户Id",required=true),
    })
    @PostMapping("/createOne")
    public ResultVO create(@RequestParam(value = "projectId") String projectId,
                           @RequestParam(value = "openid") String openid) throws Exception {
        if (projectId==null || openid == null) {
            log.error("【创建项目】参数不正确,projectId={}, openid= {}",projectId,openid);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        if (userInfoService.findUserInfoByUserOpeinid(openid) == null){
            return ResultVOUtil.error(ResultEnum.USER_NOT_FOUND);
        }

        if (projectMasterService.findProjectMasterByProjectId(projectId) == null) {
            return ResultVOUtil.error(ResultEnum.PROJECT_ID_NOT_FOUND);
        }

        if (favoritesServiceService.findFavoriteOne(openid, projectId) == null) {
            FavoritesDTO createResult =  favoritesServiceService.createFavoriteOne(openid,projectId);
            return ResultVOUtil.success(createResult);
        }
        else
            return ResultVOUtil.error(ResultEnum.FAVORITES_EXISTED);
    }

    @ApiOperation(value = "删除收藏项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId",value = "项目Id",required=true),
            @ApiImplicitParam(name = "openid",value = "用户Id",required=true),
    })
    @PostMapping("/deleteOne")
    public ResultVO deleteOne(@RequestParam(value = "projectId") String projectId,
                              @RequestParam(value = "openid") String openid){
        if (userInfoService.findUserInfoByUserOpeinid(openid) == null){
            return ResultVOUtil.error(ResultEnum.USER_NOT_FOUND);
        }
        if (projectMasterService.findProjectMasterByProjectId(projectId) == null){
            return ResultVOUtil.error(ResultEnum.PROJECT_ID_NOT_FOUND);
        }

        if (favoritesServiceService.findFavoriteOne(openid,projectId) == null){
            return ResultVOUtil.error(ResultEnum.FAVORITES_NOT_EXISTED);
        }
        return ResultVOUtil.success(favoritesServiceService.deleteFavoriteOne(openid,projectId));
    }

    @ApiOperation(value = "查询一个用户收藏的项目数", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "openid",value = "用户Id",required=true)
    @PostMapping("/checkNumByOpenId")
    public ResultVO checkNumByOpenId(@RequestParam(value = "openid") String openid){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(openid);
        if (userInfoDTO == null){
            return ResultVOUtil.error(ResultEnum.USER_NOT_FOUND);
        }
        Integer num = favoritesServiceService.findNumOfFavoriteProject(userInfoDTO.getUserId());
        return ResultVOUtil.success(num);
    }

    @ApiOperation(value = "查询一个项目被用户收藏数", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "projectId",value = "项目Id",required=true)
    @PostMapping("/checkNumByProjectId")
    public ResultVO checkNumByprojectId(@RequestParam(value = "projectId") String projectId){
        if (projectMasterService.findProjectMasterByProjectId(projectId) == null){
            return ResultVOUtil.error(ResultEnum.PROJECT_ID_NOT_FOUND);
        }
        Integer num = favoritesServiceService.findNumOfFavoriteUser(projectId);
        return ResultVOUtil.success(num);
    }

    @ApiOperation(value = "查询用户收藏所有项目的详细", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "openid",value = "用户Id",required=true)
    @PostMapping("/checkAllByOpenId")
    public ResultVO checkAllByOpenId(@RequestParam(value = "openid") String openid){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(openid);
        if (userInfoDTO == null){
            return ResultVOUtil.error(ResultEnum.USER_NOT_FOUND);
        }
        return ResultVOUtil.success(favoritesServiceService.findAllFavoriteProject(userInfoDTO.getUserId()));
    }

    @ApiOperation(value = "查询项目是否被某一用户收藏", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId",value = "项目Id",required=true),
            @ApiImplicitParam(name = "openId",value = "用户OpenId",required=true),
    })
    @PostMapping("/favoritedByUser")
    public ResultVO checkProjectFavoritedByUser(@RequestParam(value = "projectId") String projectId,
                                                @RequestParam(value = "openId") String openId){
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpeinid(openId);
        if (userInfoDTO == null){
            return ResultVOUtil.error(ResultEnum.USER_NOT_FOUND);
        }
        return ResultVOUtil.success(favoritesServiceService.checkProjectFavoritedByUser(projectId,String.valueOf(userInfoDTO.getUserId())));
    }

}
