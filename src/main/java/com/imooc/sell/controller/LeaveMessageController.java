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
public class LeaveMessageController {
    @Autowired
    LeaveMessageServiceImpl leaveMessageService;

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

    @PostMapping("/find/userOpenId")
    public ResultVO findByUserOpenId(@RequestParam(value = "openid") String userOpenId){
        if (userOpenId == null){
            log.error("【搜索留言】参数错误， userOpenId = {}", (Object) null);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        List<LeaveMessageDTO>leaveMessageDTOS = leaveMessageService.findByOpenId(userOpenId);
        return ResultVOUtil.success(leaveMessageDTOS);
    }

    @PostMapping("/find/projectId")
    public ResultVO findByProjectId(@RequestParam(value = "projectId") String projectId){
        if (projectId == null){
            log.error("【搜索留言】参数错误， projectId = {}", (Object) null);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        List<LeaveMessageDTO>leaveMessageDTOS = leaveMessageService.findByProjectId(projectId);
        return ResultVOUtil.success(leaveMessageDTOS);
    }

    @PostMapping("/delete")
    public ResultVO delete(@RequestParam(value = "openid") String userOpenId,
                           @RequestParam(value = "projectId") String projectId,
                           @RequestParam(value = "id") Integer key){
        if (userOpenId == null || projectId == null){
            log.error("【删除留言】参数错误， userOpenId = {}, projectId = {}",userOpenId,projectId);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        boolean deleteResult = leaveMessageService.deleteOne(userOpenId,projectId,key);
        return ResultVOUtil.success(deleteResult);
    }

    @PostMapping("/change")
    public ResultVO change(@RequestParam(value = "openid") String userOpenId,
                           @RequestParam(value = "projectId") String projectId,
                           @RequestParam(value = "content") String content,
                           @RequestParam(value = "id") Integer key
    ) throws Exception{
        if (userOpenId == null || projectId == null || content == null){
            log.error("【修改留言】参数错误， userOpenId = {}, projectId = {}, content = {}",userOpenId,projectId,content);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR);
        }
        LeaveMessageDTO leaveMessageDTO = leaveMessageService.changeOne(userOpenId,projectId,content,key);
        if (leaveMessageDTO == null){
            return ResultVOUtil.error(ResultEnum.CHANGE_FAILED);
        }
        return ResultVOUtil.success(leaveMessageDTO);
    }
}
