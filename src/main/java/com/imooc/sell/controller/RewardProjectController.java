package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.controller.form.PurchasingProjectFrom;
import com.imooc.sell.controller.form.RewardProjectFrom;
import com.imooc.sell.converter.PurchasingProjectFrom2PurchasingDTOConverter;
import com.imooc.sell.converter.RewardProjectFrom2RewardDTOConverter;
import com.imooc.sell.dto.PurchasingProjectDTO;
import com.imooc.sell.dto.RewardProjectDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.impl.ProjectMasterServiceImpl;
import com.imooc.sell.service.impl.PurchasingServiceImpl;
import com.imooc.sell.service.impl.RewardServiceImpl;
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
@RequestMapping("/project/reward")
@Slf4j

public class RewardProjectController {
    @Autowired
    ProjectMasterServiceImpl projectMasterService;
    @Autowired
    RewardServiceImpl rewardService;

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid RewardProjectFrom rewardProjectFrom,
                                                BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.error("【创建悬赏项目】参数不正确, rewardProjectFrom={}", rewardProjectFrom);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        RewardProjectDTO rewardProjectDTO = RewardProjectFrom2RewardDTOConverter.convert(rewardProjectFrom);

        RewardProjectDTO registerResult = rewardService.createRewardProject(rewardProjectDTO);

        return ResultVOUtil.success(registerResult);

    }

    @PostMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        PageRequest pageRequest = new PageRequest(page, size);
        List<RewardProjectDTO> list = rewardService.findRewardProjectsOrderByUpdateTime(pageRequest);
        return ResultVOUtil.success(list);

    }
    @PostMapping("/findOne")
    public ResultVO findOne(@RequestParam(value = "projectId") String projectId){
        return  ResultVOUtil.success(rewardService.findRewardByProjectId(projectId));
    }

    @PostMapping("/deleteOne")
    public ResultVO deleteOne(@RequestParam(value = "projectId") String projectId,
                              @RequestParam(value = "openid") String openid){
        return  ResultVOUtil.success(projectMasterService.deleateProjectMaster(projectId,openid));
    }
}
