package com.imooc.sell.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SendSmsTest {

    @Test
    public void newCaptcha() {
        String ca = SendSms.newCaptcha(4);
        Assert.assertEquals(4,ca.length());
    }

    @Test
    public void sendSms() {
//        String captch = SendSms.newCaptcha(4);
//        Boolean result = SendSms.sendSms(captch,"15627361826","4");
        String captch = "1024997\n尊敬的 确实憨 您好! \n感谢您参加由[武汉探店]举办的双十一抽奖活动。\n恭喜您中得特殊奖，将会有工作人员在3个工作日内联系您，届时请您提供收货地址与验证码，奖品将会以快递的形式赠与您！并且当工作人员联系您后\n";
        Boolean result = SendSms.sendSms(captch,"13608334908","4");
        Assert.assertEquals(result,true);

    }
}