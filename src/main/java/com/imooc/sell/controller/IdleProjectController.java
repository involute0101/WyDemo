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

public class IdleProjectController {
    @Autowired
    ProjectMasterServiceImpl projectMasterService;
    @Autowired
    IdleProjectServiceImpl idleProjectService;

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

    @PostMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        PageRequest pageRequest = new PageRequest(page, size);
        List<IdleProjectDTO> list = idleProjectService.findIdleProjectsOrderByUpdateTime(pageRequest);
        return ResultVOUtil.success(list);

    }
    @PostMapping("/findOne")
    public ResultVO findOne(@RequestParam(value = "projectId") String projectId){
        return  ResultVOUtil.success(idleProjectService.findIdleProjectByProjectId(projectId));
    }

    @PostMapping("/deleteOne")
    public ResultVO deleteOne(@RequestParam(value = "projectId") String projectId,
                              @RequestParam(value = "openid") String openid){
        return  ResultVOUtil.success(projectMasterService.deleateProjectMaster(projectId,openid));
    }
}
