package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.controller.form.LostPropertyProjectFrom;
import com.imooc.sell.converter.LostPropertyProjectFrom2LostPropertyProjectDTOConverter;
import com.imooc.sell.dto.LostPropertyProjectDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.impl.LostPropertyProjectServiceImpl;
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
@RequestMapping("/project/lostProperty")
@Slf4j

public class LostPropertyProjectController {
    @Autowired
    ProjectMasterServiceImpl projectMasterService;
    @Autowired
    LostPropertyProjectServiceImpl lostPropertyProjectService;

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid LostPropertyProjectFrom lostPropertyProjectFrom,
                                                BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.error("【创建失物招领项目】参数不正确, lostPropertyProjectFrom={}", lostPropertyProjectFrom);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        LostPropertyProjectDTO lostPropertyProjectDTO = LostPropertyProjectFrom2LostPropertyProjectDTOConverter.convert(lostPropertyProjectFrom);

        LostPropertyProjectDTO registerResult = lostPropertyProjectService.createLostPropertyProject(lostPropertyProjectDTO);

        return ResultVOUtil.success(registerResult);

    }

    @PostMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        PageRequest pageRequest = new PageRequest(page, size);
        List<LostPropertyProjectDTO> list = lostPropertyProjectService.findLostPropertyProjectsOrderByUpdateTime(pageRequest);
        return ResultVOUtil.success(list);

    }
    @PostMapping("/findOne")
    public ResultVO findOne(@RequestParam(value = "projectId") String projectId){
        return  ResultVOUtil.success(lostPropertyProjectService.findLostPropertyProjectByProjectId(projectId));
    }

    @PostMapping("/deleteOne")
    public ResultVO deleteOne(@RequestParam(value = "projectId") String projectId,
                              @RequestParam(value = "openid") String openid){
        return  ResultVOUtil.success(projectMasterService.deleateProjectMaster(projectId,openid));
    }
}
