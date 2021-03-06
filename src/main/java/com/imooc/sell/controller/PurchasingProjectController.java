package com.imooc.sell.controller;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.controller.form.LostPropertyProjectFrom;
import com.imooc.sell.controller.form.PurchasingProjectFrom;
import com.imooc.sell.converter.LostPropertyProjectFrom2LostPropertyProjectDTOConverter;
import com.imooc.sell.converter.PurchasingProjectFrom2PurchasingDTOConverter;
import com.imooc.sell.dto.*;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.impl.LostPropertyProjectServiceImpl;
import com.imooc.sell.service.impl.ProjectMasterServiceImpl;
import com.imooc.sell.service.impl.PurchasingServiceImpl;
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
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/project/purchasing")
@Slf4j
@Api(tags = "跑腿项目-访问接口")
public class PurchasingProjectController {
    @Autowired
    ProjectMasterServiceImpl projectMasterService;
    @Autowired
    PurchasingServiceImpl purchasingService;

    @Autowired
    UserInfoServiceImpl userInfoService;

    @ApiOperation(value = "创建跑腿项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid PurchasingProjectFrom purchasingProjectFrom,
                                                BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.error("【创建跑腿项目】参数不正确, purchasingProjectFrom={}", purchasingProjectFrom);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        PurchasingProjectDTO purchasingProjectDTO = PurchasingProjectFrom2PurchasingDTOConverter.convert(purchasingProjectFrom);

        PurchasingProjectDTO registerResult = purchasingService.createPurchasingProject(purchasingProjectDTO);

        return ResultVOUtil.success(registerResult);

    }

    @ApiOperation(value = "按照更新时间查询跑腿项目", notes = "")
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
        List<PurchasingProjectDTO> list = purchasingService.findPurchasingProjectsOrderByUpdateTime(pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(PurchasingProjectDTO purchasingProjectDTO : list){
            JSONObject purchasingProjectInfo = JSONObject.parseObject(purchasingProjectDTO.toString());
            UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(purchasingProjectDTO.getUserOpenId());
            purchasingProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
            purchasingProjectInfo.put("userName",userInfoDTO.getUserName());
            result.add(purchasingProjectInfo);
        }
        return ResultVOUtil.success(result);

    }

    @ApiOperation(value = "按照项目id查找项目详情", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "projectId",value = "项目id",required=true)
    @PostMapping("/findOne")
    public ResultVO findOne(@RequestParam(value = "projectId") String projectId){
        PurchasingProjectDTO purchasingProjectDTO = purchasingService.findPurchasingByProjectId(projectId);
        JSONObject purchasingProjectInfo = JSONObject.parseObject(purchasingProjectDTO.toString());
        UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(purchasingProjectDTO.getUserOpenId());
        purchasingProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
        purchasingProjectInfo.put("userName",userInfoDTO.getUserName());
        return ResultVOUtil.success(purchasingProjectInfo);
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

    @ApiOperation(value = "根据标签查找 跑腿项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
            @ApiImplicitParam(name = "tagKeyword",value = "标签（的关键字）",required=true),
    })
    @PostMapping("/tagLike")
    public ResultVO findByTagLike(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                  @RequestParam(value = "tagKeyword", defaultValue = "") String tagKeyword) {
        if (page <= 0) return ResultVOUtil.error(403, "请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page - 1, size);
        List<PurchasingProjectDTO> list = purchasingService.findPurchasingProjectByTagsLike(tagKeyword, pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(PurchasingProjectDTO purchasingProjectDTO : list){
            JSONObject purchasingProjectInfo = JSONObject.parseObject(purchasingProjectDTO.toString());
            UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(purchasingProjectDTO.getUserOpenId());
            purchasingProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
            purchasingProjectInfo.put("userName",userInfoDTO.getUserName());
            result.add(purchasingProjectInfo);
        }
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "按照悬赏金额大小排序,查询跑腿项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
            @ApiImplicitParam(name = "sort",value = "排序方式（asc/desc）",required=true),
    })
    @PostMapping("/orderByAmount")
    public ResultVO findOrderByAmount(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size,
                                      @RequestParam(value = "sort", defaultValue = "asc") String sort){
        if (page<=0)return ResultVOUtil.error(403,"请求页不合规范！");
        if(!"desc".equals(sort) && !"asc".equals(sort))return ResultVOUtil.error(403,"排序方式不正确");
        PageRequest pageRequest = new PageRequest(page-1,size);
        List<PurchasingProjectDTO> list = purchasingService.findPurchasingProjectOrderByAmount(pageRequest, sort);

        List<JSONObject> result = new ArrayList<>();
        for(PurchasingProjectDTO purchasingProjectDTO : list){
            JSONObject purchasingProjectInfo = JSONObject.parseObject(purchasingProjectDTO.toString());
            UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(purchasingProjectDTO.getUserOpenId());
            purchasingProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
            purchasingProjectInfo.put("userName",userInfoDTO.getUserName());
            result.add(purchasingProjectInfo);
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
        List<PurchasingProjectDTO> list = purchasingService.findByPurchasingProjectByTitleLike(titleKeyword, pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(PurchasingProjectDTO purchasingProjectDTO : list){
            JSONObject purchasingProjectInfo = JSONObject.parseObject(purchasingProjectDTO.toString());
            UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(purchasingProjectDTO.getUserOpenId());
            purchasingProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
            purchasingProjectInfo.put("userName",userInfoDTO.getUserName());
            result.add(purchasingProjectInfo);
        }
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "按照 热度 查询跑腿项目", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",required=true),
            @ApiImplicitParam(name = "size",value = "页大小",required=true),
    })
    @PostMapping("/popular")
    public ResultVO findPurchasigProjectOrderByFavoritesNumber(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        if (page <= 0) return ResultVOUtil.error(403, "请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page - 1, size);
        List<PurchasingProjectDTO> list = purchasingService.findPurchasingProjectOrderByFavoritesNumber(pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(PurchasingProjectDTO purchasingProjectDTO : list){
            JSONObject purchasingProjectInfo = JSONObject.parseObject(purchasingProjectDTO.toString());
            UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(purchasingProjectDTO.getUserOpenId());
            purchasingProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
            purchasingProjectInfo.put("userName",userInfoDTO.getUserName());
            result.add(purchasingProjectInfo);
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
    public ResultVO findPurchasingProjectComplex(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                             @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        if (page <= 0) return ResultVOUtil.error(403, "请求页不合规范！");
        PageRequest pageRequest = new PageRequest(page - 1, size);
        List<PurchasingProjectDTO> list = purchasingService.findByComplexService(pageRequest);

        List<JSONObject> result = new ArrayList<>();
        for(PurchasingProjectDTO purchasingProjectDTO : list){
            JSONObject purchasingProjectInfo = JSONObject.parseObject(purchasingProjectDTO.toString());
            UserInfoDTO userInfoDTO = userInfoService.findUserInfoByUserOpenId(purchasingProjectDTO.getUserOpenId());
            purchasingProjectInfo.put("headPortrait",userInfoDTO.getHeadPortrait());
            purchasingProjectInfo.put("userName",userInfoDTO.getUserName());
            result.add(purchasingProjectInfo);
        }
        return ResultVOUtil.success(result);
    }

    @ApiOperation(value = "增加浏览量", notes = "")
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @ApiImplicitParam(name = "projectId",value = "项目id",required=true)
    @PostMapping("/pageviews")
    public ResultVO incPageviews(@RequestParam(value = "projectId") String projectId){
        return ResultVOUtil.success(purchasingService.increasePageviews(projectId));
    }
}
