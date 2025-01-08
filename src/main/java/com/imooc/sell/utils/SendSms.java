package com.imooc.sell.utils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.sms.v20190711.SmsClient;

import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

import java.util.Random;

public class SendSms
{
    public static synchronized String newCaptcha(int len){
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < len; i++) {
            rs.append(r.nextInt(10));
        }
        return rs.toString();
    }
    public static boolean sendSms (String captcha ,String phoneNumbers, String limitTime) {
        try{

            Credential cred = new Credential("密钥ID", "密钥key");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            phoneNumbers = "+86"+phoneNumbers;

            String params = "{\"PhoneNumberSet\":[\""+phoneNumbers+"\"],\"TemplateID\":\"1036349\",\"Sign\":\"理智好文笔\",\"TemplateParamSet\":[\""+captcha+"\",\""+limitTime+"\"],\"SmsSdkAppid\":\"1400546756\"}";
            SendSmsRequest req = SendSmsRequest.fromJsonString(params, SendSmsRequest.class);

            SendSmsResponse resp = client.SendSms(req);

            System.out.println(SendSmsRequest.toJsonString(resp));
            return true;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return false;
        }

    }

}
