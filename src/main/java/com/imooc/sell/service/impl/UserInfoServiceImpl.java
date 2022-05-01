package com.imooc.sell.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.VO.CaptchaVO;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dataobject.*;
import com.imooc.sell.dto.*;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.*;
import com.imooc.sell.service.UserInfoService;
import com.imooc.sell.utils.ResultVOUtil;
import com.imooc.sell.utils.SendSms;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoRepository userInfoRepository;


    @Autowired
    TagRepository tagRepository;

    @Autowired
    IdleProjectRepository idleProjectRepository;

    @Autowired
    private JobsProjectRepository jobsProjectRepository;

    @Autowired
    private LostPropertyProjectRepository lostPropertyProjectRepository;

    @Autowired
    private PurchasingProjectRepository purchasingProjectRepository;

    @Autowired
    private RewardProjectRepository rewardProjectRepository;

    @Autowired
    private StudyProjectRepository studyProjectRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

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

    public UserInfoDTO findUserInfoByUserOpenId(String openId){
        UserInfo userInfo = userInfoRepository.findByUserOpenid(openId);
        if(userInfo == null)
        {
            log.error("[登录] 登陆失败,账号不存在");
            throw new SellException (ResultEnum.LOGIN_FAIL);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo, userInfoDTO);
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
        userInfoResult.setStudentId(userInfoDTO.getStudentId());
        userInfoResult.setCertification(1);
        userInfoResult.setStudentCardPhotos(userInfoDTO.getStudentCardPhotos());
        UserInfo userInfo = userInfoRepository.save(userInfoResult);
        UserInfoDTO result = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo,result);
        return result;
    }

    /**
     * 获取用户openId
     * @param code 授权码
     * @return
     * @throws IOException
     */
    @Override
    public String getOpenId(String code) throws IOException {
        log.info("用户授权码："+code);
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=wxfe433c89a52a7781";
        url += "&secret=9e601f34864fd93570135190d30c308e";
        url += "&js_code=" + code;
        url += "&grant_type=authorization_code";
        url += "&connect_redirect=1";
        String res = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)        // 设置连接超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(false).build();
        httpget.setConfig(requestConfig);
        response = httpClient.execute(httpget);
        HttpEntity responseEntity = response.getEntity();
        log.info("响应状态为:" + response.getStatusLine());
        if (responseEntity != null) {
            res = EntityUtils.toString(responseEntity);
            log.info("响应内容长度为:" + responseEntity.getContentLength());
            log.info("响应内容为:" + res);
        }
        if (httpClient != null) {
            httpClient.close();
        }
        if (response != null) {
            response.close();
        }
        JSONObject jo = JSON.parseObject(res);
        String openid = jo.getString("openid");
        return openid;
    }

    /**
     * 用户加入圈子
     * @param userOpenId 用户openId
     * @param circleName 圈子名称
     * @return
     */
    @Override
    @Transactional
    public ResultVO joinDiscussionCircle(String userOpenId, String circleName) {
        boolean flag = false;
        UserInfo userInfo = userInfoRepository.findByUserOpenid(userOpenId);
        if(userInfo==null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        //判断圈子是否存在
        Tag circle = tagRepository.findByTagContent(circleName);
        if(circle==null){
            throw new SellException(ResultEnum.CIRCLE_NOT_FOUND);
        }
        //判断重复加入圈子,重复加入则退出
        String discussionCircle = userInfo.getDiscussionCircle();
        if(discussionCircle==null || discussionCircle.equals("")){
            discussionCircle = circleName;
        }
        else if(discussionCircle.contains(circleName)){
            String[] splits = discussionCircle.split(",");
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<splits.length;i++){
                if(!splits[i].equals(circleName)){
                    if(i>0)sb.append(",");
                    sb.append(splits[i]);
                }
            }
            discussionCircle = sb.toString();
            flag = true;
        }
        else {
            discussionCircle += (","+circleName);
        }
        if(!flag)circle.setPersonNumber(circle.getPersonNumber()+1);
        else circle.setPersonNumber(circle.getPersonNumber()-1);
        tagRepository.save(circle);
        userInfo.setDiscussionCircle(discussionCircle);
        userInfoRepository.save(userInfo);
        if(!flag) return ResultVOUtil.success("加入圈子 "+circleName+" 成功！");
        return ResultVOUtil.success("您已退出 "+circleName+" 圈子");
    }

    /**
     * 根据openId，检查用户是否加入圈子
     * @param userOpenId 用户openId
     * @param circleName 圈子名称
     * @return
     */
    @Override
    public boolean checkUserJoinCircle(String userOpenId, String circleName) {
        UserInfo userInfo = userInfoRepository.findByUserOpenid(userOpenId);
        if(userInfo==null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        String discussionCircle = userInfo.getDiscussionCircle();
        if(discussionCircle==null)return false;
        if(discussionCircle.contains(circleName))return true;
        return false;
    }

    /**
     * 根据openId获得用户发布过的所有项目
     * @param userOpenId 用户openId
     * @return
     */
    @Override
    public List<JSONObject> findProjectByUserOpenId(String userOpenId) {
        UserInfo userInfo = userInfoRepository.findByUserOpenid(userOpenId);
        if(userInfo==null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        List<JSONObject> result = new ArrayList<>();
        List<IdleProject> idleProjectList = idleProjectRepository.findByUserOpenId(userOpenId);
        for(IdleProject idleProject:idleProjectList){
            IdleProjectDTO idleProjectDTO = new IdleProjectDTO();
            BeanUtils.copyProperties(idleProject, idleProjectDTO,"picture");
            if (idleProject.getPicture()!=null){
                idleProjectDTO.setPicture(idleProject.getPicture().split(","));
            }
            JSONObject projectInfo = JSONObject.parseObject(idleProjectDTO.toString());
            projectInfo.put("headPortrait",userInfo.getHeadPortrait());
            projectInfo.put("userName",userInfo.getUserName());
            result.add(projectInfo);
        }
        List<JobsProject> jobsProjectList = jobsProjectRepository.findByUserOpenId(userOpenId);
        for(JobsProject jobsProject : jobsProjectList){
            JobsProjectDTO jobsProjectDTO = new JobsProjectDTO();
            BeanUtils.copyProperties(jobsProject, jobsProjectDTO,"picture");
            if (jobsProject.getPicture()!=null){
                jobsProjectDTO.setPicture(jobsProject.getPicture().split(","));
            }
            JSONObject projectInfo = JSONObject.parseObject(jobsProjectDTO.toString());
            projectInfo.put("headPortrait",userInfo.getHeadPortrait());
            projectInfo.put("userName",userInfo.getUserName());
            result.add(projectInfo);
        }
        List<LostPropertyProject> lostPropertyProjectsList = lostPropertyProjectRepository.findByUserOpenId(userOpenId);
        for(LostPropertyProject lostPropertyProject:lostPropertyProjectsList){
            LostPropertyProjectDTO lostPropertyProjectDTO = new LostPropertyProjectDTO();
            BeanUtils.copyProperties(lostPropertyProject, lostPropertyProjectDTO,"picture");
            if(lostPropertyProject.getPicture()!=null){
                lostPropertyProjectDTO.setPicture(lostPropertyProject.getPicture().split(","));
            }
            JSONObject projectInfo = JSONObject.parseObject(lostPropertyProjectDTO.toString());
            projectInfo.put("headPortrait",userInfo.getHeadPortrait());
            projectInfo.put("userName",userInfo.getUserName());
            result.add(projectInfo);
        }
        List<PurchasingProject> purchasingProjectList = purchasingProjectRepository.findByUserOpenId(userOpenId);
        for(PurchasingProject purchasingProject : purchasingProjectList){
            PurchasingProjectDTO purchasingProjectDTO = new PurchasingProjectDTO();
            BeanUtils.copyProperties(purchasingProject, purchasingProjectDTO,"picture");
            if(purchasingProject.getPicture()!=null){
                purchasingProjectDTO.setPicture(purchasingProject.getPicture().split(","));
            }
            JSONObject projectInfo = JSONObject.parseObject(purchasingProjectDTO.toString());
            projectInfo.put("headPortrait",userInfo.getHeadPortrait());
            projectInfo.put("userName",userInfo.getUserName());
            result.add(projectInfo);
        }
        List<RewardProject> rewardProjectList = rewardProjectRepository.findByUserOpenId(userOpenId);
        for(RewardProject rewardProject : rewardProjectList){
            RewardProjectDTO rewardProjectDTO = new RewardProjectDTO();
            BeanUtils.copyProperties(rewardProject, rewardProjectDTO,"picture");
            if(rewardProject.getPicture()!=null){
                rewardProjectDTO.setPicture(rewardProject.getPicture().split(","));
            }
            JSONObject projectInfo = JSONObject.parseObject(rewardProjectDTO.toString());
            projectInfo.put("headPortrait",userInfo.getHeadPortrait());
            projectInfo.put("userName",userInfo.getUserName());
            result.add(projectInfo);
        }
        List<StudyProject> studyProjectsList = studyProjectRepository.findByUserOpenId(userOpenId);
        for(StudyProject studyProject : studyProjectsList){
            StudyProjectDTO studyProjectDTO = new StudyProjectDTO();
            BeanUtils.copyProperties(studyProject, studyProjectDTO,"picture");
            if (studyProject.getPicture()!=null){
                studyProjectDTO.setPicture(studyProject.getPicture().split(","));
            }
            JSONObject projectInfo = JSONObject.parseObject(studyProjectDTO.toString());
            projectInfo.put("headPortrait",userInfo.getHeadPortrait());
            projectInfo.put("userName",userInfo.getUserName());
            result.add(projectInfo);
        }
        return result;
    }

    @Override
    public List<JSONObject> getProjectByFollow(String userOpenId) {
        UserInfo userInfo = userInfoRepository.findByUserOpenid(userOpenId);
        if(userInfo==null){
            throw new SellException(ResultEnum.USER_NOT_FOUND);
        }
        Page<UserFollow> page = userFollowRepository.findByUserOpenId(userOpenId, new PageRequest(0, 10000));
        List<JSONObject> result = new ArrayList<>();
        for(UserFollow userFollow : page){
            List<JSONObject> followProject = findProjectByUserOpenId(userFollow.getGoalFollower());
            result.addAll(followProject);
        }
        return result;
    }
}
