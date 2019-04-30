package com.example.ExamSys.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//自动加载配置文件前缀是user.aliyun.smsCode的配置文件
@ConfigurationProperties(prefix = "aliyun.smsCode")
//配置文件的启用条件 当user.smsService.component的值为aliyunSmcodeService时 该配置文件才会被启用
@ConditionalOnProperty(name = "user.smsService.component",havingValue = "aliyunSmcodeService")
//配置文件地址
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class AliyunSmsCodeProperties {
	
	private String accessId;
	
	private String accessKey;

	private String regionId;
	
	private String signName;
	
	private String templateCode;
	
	private String version;
	
	private String action;
	
	private String domain;

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
}
