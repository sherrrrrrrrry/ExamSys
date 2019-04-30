package com.example.ExamSys.service;

import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.example.ExamSys.config.AliyunSmsCodeProperties;


@Service
public class PhoneService {
	
	@Autowired
	AliyunSmsCodeProperties aliyunSmsCodeProperties;
	
	private final Logger log = LoggerFactory.getLogger(PhoneService.class);
	
	/**
	 * 发送短信
	 * @param to：目标手机号
	 * @param content：验证码
	 * @return Boolean：成功，True；失败，False；String：成功：'ok'，失败：失败原因
	 */
	public HashMap<Boolean, String> sendSimpleMessage(String to, String content){

		String templateParam = "{\"code\":\"" + content + "\"}";
		
		DefaultProfile profile = DefaultProfile.getProfile(aliyunSmsCodeProperties.getRegionId(), aliyunSmsCodeProperties.getAccessId(), aliyunSmsCodeProperties.getAccessKey());
        IAcsClient client = new DefaultAcsClient(profile);
        
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(aliyunSmsCodeProperties.getDomain());
        request.setVersion(aliyunSmsCodeProperties.getVersion());
        request.setAction(aliyunSmsCodeProperties.getAction());
        request.putQueryParameter("RegionId", aliyunSmsCodeProperties.getRegionId());
        request.putQueryParameter("TemplateParam", templateParam);
        request.putQueryParameter("PhoneNumbers", to);
        request.putQueryParameter("SignName", aliyunSmsCodeProperties.getSignName());
        request.putQueryParameter("TemplateCode", aliyunSmsCodeProperties.getTemplateCode());
        
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject json_test = new JSONObject(response.getData());
            if(json_test.getString("Code").equals("OK")) {
                HashMap<Boolean, String> results = new HashMap<Boolean, String>();
                results.put(true, "ok");
                return results; 
            } else {
            	HashMap<Boolean, String> results = new HashMap<Boolean, String>();
                results.put(false, json_test.getString("Message"));
                return results; 
            }
        } catch (ServerException e) {
        	log.warn(e.toString());
        	HashMap<Boolean, String> results = new HashMap<Boolean, String>();
            results.put(false, "Message Server Wrong!");
            return results; 
        } catch (ClientException e) {
        	log.warn(e.toString());
        	HashMap<Boolean, String> results = new HashMap<Boolean, String>();
            results.put(false, "Message Client Wrong!");
            return results; 
        } catch(Exception e) {
        	log.warn(e.toString());
        	HashMap<Boolean, String> results = new HashMap<Boolean, String>();
            results.put(false, "Data Analyze Wrong!");
            return results; 
        }
	}
	
	public HashMap<Boolean, String> test(String to, String content){

		String templateParam = "{\"code\":\"" + content + "\"}";
		System.out.println(templateParam);
		
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIefQZ4rYSSKTE", "uRkvGELwDwiRhgcA3Qr8Y8OsdU5ZJ1");
        IAcsClient client = new DefaultAcsClient(profile);
        
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("TemplateParam", templateParam);
        request.putQueryParameter("PhoneNumbers", to);
        request.putQueryParameter("SignName", "核心素养潜能测评系统");
        request.putQueryParameter("TemplateCode", "SMS_164511034");
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject json_test = new JSONObject(response.getData());
            if(json_test.getString("Code").equals("OK")) {
                HashMap<Boolean, String> results = new HashMap<Boolean, String>();
                results.put(true, "ok");
                return results; 
            } else {
            	HashMap<Boolean, String> results = new HashMap<Boolean, String>();
                results.put(false, json_test.getString("Message"));
                return results; 
            }
        } catch (ServerException e) {
        	log.warn(e.toString());
        	HashMap<Boolean, String> results = new HashMap<Boolean, String>();
            results.put(false, "Message Server Wrong!");
            return results; 
        } catch (ClientException e) {
        	log.warn(e.toString());
        	HashMap<Boolean, String> results = new HashMap<Boolean, String>();
            results.put(false, "Message Client Wrong!");
            return results; 
        } catch(Exception e) {
        	log.warn(e.toString());
        	HashMap<Boolean, String> results = new HashMap<Boolean, String>();
            results.put(false, "Data Analyze Wrong!");
            return results; 
        }
	}
}
