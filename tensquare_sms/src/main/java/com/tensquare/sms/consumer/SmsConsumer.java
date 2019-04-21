package com.tensquare.sms.consumer;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/20
 * @Description: com.tensquare.sms.consumer
 * @version: 1.0
 */
@Component
@RabbitListener(queues = "sms")
public class SmsConsumer {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;
    @Value("${aliyun.sms.template_code}")
    private String template_code;

    @RabbitHandler
    public void executeSms(Map<String, String> map){
        System.out.println("手机号:" + map.get("mobile"));
        System.out.println("验证码:" + map.get("checkcode"));
        String checkcode = map.get("checkcode");
        String mobile = map.get("mobile");
        try {
            //验证码:{key:value}
            smsUtil.sendSms(mobile, template_code, sign_name, "{\"checkcode\":\"" + checkcode + "\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
