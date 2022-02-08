package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.impl.CommonService;
import com.imooc.sell.utils.ResultVOUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/util")
@Slf4j
@Api(tags = "通用功能接口")
public class CommonContrller {

    @Autowired
    private CommonService commonService;

    @ApiOperation(value = "上传文件", notes = "返回上传后文件在服务器的地址")
    @ApiImplicitParams({@ApiImplicitParam(name = "file",value = "文件（图片）",required=true),
            @ApiImplicitParam(name = "userOpenId",value = "用户openId",required=true)})
    @ApiResponses({@ApiResponse(code = 200, message = "成功"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")})
    @PostMapping("/uploadFile")
    public ResultVO uploadFile(@RequestParam("file") MultipartFile file,
                               @RequestParam("userOpenId") String userOpenId){
        try{
            String filePath = commonService.uploadFile(file, userOpenId);
            return ResultVOUtil.success(filePath);
        }catch (SellException e){
            return ResultVOUtil.error(403,"上传失败，原因："+e.getMessage());
        }
    }
}
