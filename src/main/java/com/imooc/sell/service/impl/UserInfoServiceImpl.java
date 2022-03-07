package com.imooc.sell.service.impl;

import com.imooc.sell.VO.CaptchaVO;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dataobject.UserInfo;
import com.imooc.sell.dto.UserInfoDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.UserInfoRepository;
import com.imooc.sell.service.UserInfoService;
import com.imooc.sell.utils.ResultVOUtil;
import com.imooc.sell.utils.SendSms;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoRepository userInfoRepository;

    //注册
    @Override
    @Transactional
    public UserInfoDTO createUserInfoOne(UserInfoDTO userInfoDTO) throws Exception{
        UserInfo userInfo = new UserInfo();
        //判断Openid是否已经注册过
       UserInfo result = userInfoRepository.findByUserOpenid(userInfoDTO.getUserOpenid());
       if (result != null){
           log.error("[注册用户] 注册失败，openid = {} 已被注册", userInfoDTO.getUserOpenid());
           throw new SellException(ResultEnum.REGISTER_FAIL);
       }

       /*
        //将上传的头像存储到本地
        String path = "C:/Users/Administrator/Desktop/FourmPicture/"
                +userInfoDTO.getUserOpenid()+"_avatar.png";
        //判断文件是否存在
        File file = new File(path);
        if (!file.exists())
        {
            log.error("[注册用户] 头像保存失败");
            throw new SellException(ResultEnum.AVATAR_STORE_FAIL);
        }

        */

        //将用户信息写入到数据库
        BeanUtils.copyProperties(userInfoDTO,userInfo);
        userInfoRepository.save(userInfo);
        return userInfoDTO;
    }

    @Override
    @Transactional
    public UserInfo updateUserInfo(UserInfoDTO userInfoDTO) {
        String userOpenid = userInfoDTO.getUserOpenid();
        UserInfo userInfobyUserOpenid = userInfoRepository.findByUserOpenid(userOpenid);
        if(userInfobyUserOpenid==null)throw new SellException(ResultEnum.USER_NOT_FOUND);
        userInfobyUserOpenid.setQqNumber(userInfoDTO.getQqNumber());
        userInfobyUserOpenid.setWeChat(userInfoDTO.getWeChat());
        userInfobyUserOpenid.setHeadPortrait(userInfoDTO.getHeadPortrait());
        userInfobyUserOpenid.setUserName(userInfoDTO.getUserName());
        userInfobyUserOpenid.setTelephone(userInfoDTO.getTelephone());
        UserInfo saveResult = userInfoRepository.save(userInfobyUserOpenid);
        return saveResult;
    }


    //登录
    public UserInfoDTO findUserInfoByBuyerOpenidAndPassword(String openid, String password){
        UserInfo userInfo = userInfoRepository.findByUserOpenid(openid);
        if(userInfo == null)
        {
            log.error("[登录] 登陆失败,账号或密码错误");
            throw new SellException (ResultEnum.LOGIN_FAIL);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();

        BeanUtils.copyProperties(userInfo, userInfoDTO);
        if(!userInfoDTO.getUserPassword().equals(password))
        {
            log.error("[登录] 登陆失败,账号或密码错误");
            throw new SellException (ResultEnum.LOGIN_FAIL);
        }
        return userInfoDTO;
    }

    //验证
    @Override
    public CaptchaVO sendSms(String telephone){
        String aCaptcha = SendSms.newCaptcha(6);
        SendSms.sendSms(aCaptcha,telephone,"2");
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setPhoneNumbers(telephone);
        captchaVO.setCaptcha(aCaptcha);
        return captchaVO;
    }

    //防止账号重复
    @Override
    public UserInfoDTO checkUserInfoByBuyerOpenid(String openid){
        UserInfo userInfo = userInfoRepository.findByUserOpenid(openid);
        if(userInfo == null)
        {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setAnotherInfo("该账号尚未被注册");
            return userInfoDTO;
        }
        else {
            log.error("[检查] 该账号已注册");
            throw new SellException (ResultEnum.REGISTER_FAIL);
        }
    }

    @Override
    public UserInfoDTO findUserInfoByUserOpeinid(String openid) {
        UserInfo userInfo = userInfoRepository.findByUserOpenid(openid);
        UserInfoDTO result = new UserInfoDTO();
        if (userInfo == null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        BeanUtils.copyProperties(userInfo,result);
        return result;
    }

    @Override
    public List<UserInfoDTO> findUserInfos(Collection<Integer> collection) {
        List<UserInfo> userInfos = userInfoRepository.findByUserIdIn(collection);
        List<UserInfoDTO> userInfoDTOS = new ArrayList<>();
        if (userInfos != null){
            for (UserInfo userInfo: userInfos){
                UserInfoDTO userInfoDTO = new UserInfoDTO();
                BeanUtils.copyProperties(userInfo,userInfoDTO);
                userInfoDTOS.add(userInfoDTO);
            }
        }
        return userInfoDTOS;
    }

    @Override
    public UserInfoDTO findByUserId(Integer userId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo,userInfoDTO);
        return userInfoDTO;
    }

    /**
     * 修改用户密码
     * @param userOpenId 用户openId
     * @param newPassword   新密码
     * @return
     */
    @Override
    @Transactional
    public ResultVO modifyPassword(String userOpenId, String newPassword){
        UserInfo userInfo = userInfoRepository.findByUserOpenid(userOpenId);
        if(userInfo==null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        userInfo.setUserPassword(newPassword);
        userInfoRepository.save(userInfo);
        return ResultVOUtil.success("修改密码成功！");
    }

    /**
     * 用户进行学生认证
     * @param userInfoDTO
     * @return
     */
    @Override
    @Transactional
    public UserInfoDTO studentCertification(UserInfoDTO userInfoDTO) {
        UserInfo userInfoResult = userInfoRepository.findByUserOpenid(userInfoDTO.getUserOpenid());
        if(userInfoResult==null)throw new SellException(ResultEnum.USER_NOT_FOUND);
        userInfoResult.setUserSex(userInfoDTO.getUserSex());
        userInfoResult.setUserRealName(userInfoDTO.getUserRealName());
        userInfoResult.setUserUniversity(userInfoDTO.getUserUniversity());
        userInfoResult.setUserMajor(userInfoDTO.getUserMajor());
        userInfoResult.setUserCollege(userInfoDTO.getUserCollege());
        userInfoResult.setUserDegree(userInfoDTO.getUserDegree());
        userInfoResult.setCertification(1);
        userInfoResult.setStudentCardPhotos(userInfoDTO.getStudentCardPhotos());
        UserInfo userInfo = userInfoRepository.save(userInfoResult);
        UserInfoDTO result = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo,result);
        return result;
    }
}
