package com.example.ExamSys.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExamSys.dao.ExcellentProductionRepository;
import com.example.ExamSys.dao.QuestionAnswerRepository;
import com.example.ExamSys.domain.ExcellentProduction;
import com.example.ExamSys.domain.QuestionAnswer;

@RestController
@RequestMapping("/teacher")
public class ExcellentProductionController {
	private final Logger log = LoggerFactory.getLogger(ExcellentProductionController.class);
	
	@Autowired
	QuestionAnswerRepository questionAnswerRepository;
	
	@Autowired
	ExcellentProductionRepository excellentProductionRepository;
	
	/**
	 * 从数据库中提取30天内，得分靠前的作品展示题
	 * @param pageNumber : 前端页码，第几页
	 * @return questionAnswerId: 该条记录在QuestionAnswer表中的id
	 * 			studentName: 该学生的login
	 * 			production: BASE64编码的String数组
	 */
	@RequestMapping(value = "/getProductions", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> getProductions(@RequestParam(value = "pageNumber") int pageNumber){
		
		Pageable pr = new PageRequest(pageNumber*6, 6);
		
		Date date = new Date();
		Calendar no = Calendar.getInstance();
		no.setTime(date);
		no.set(Calendar.DATE, no.get(Calendar.DATE)-30);
		Date newDate = no.getTime();
		Page<QuestionAnswer> questionAnswerPage= questionAnswerRepository.findByNumberContaining(pr, newDate);
		List<QuestionAnswer> questionAnswerList = questionAnswerPage.getContent();
	
		JSONArray jsonArr = new JSONArray();
		for(QuestionAnswer questionAnswer : questionAnswerList) {
			JSONObject jsonObj = new JSONObject();
			ArrayList<String> productions = new ArrayList<String>();
			try {					
				jsonObj.put("questionAnswerId", questionAnswer.getId());
				jsonObj.put("studentName", questionAnswer.getStudent().getUser().getLogin());
			} catch(Exception e) {
				e.printStackTrace();
				log.info("getProductions Construct json data wrong!");
				return ResponseEntity.badRequest().header("Json","Construct json data wrong!").body(null);
			}
			String temp_url = questionAnswer.getAnswer();
			String[] urls = temp_url.split("<==>");
			
			// 处理作品图片
			for(int i=1 ; i<urls.length;i++) {
				try {
					File file = new File(urls[i]);
					FileInputStream inputStream = new FileInputStream(file);
	
					byte[] bytes = new byte[inputStream.available()];
	
					inputStream.read(bytes, 0, inputStream.available());
					inputStream.close();				
					String data = Base64.encodeBase64String(bytes);
					
					productions.add(data);
				} catch(Exception e) {
					e.printStackTrace();
					log.info("getProductions 图片读取错误");
					continue;
				}
			}
			try {
				if(productions.size() > 0) {
					StringBuilder sb = new StringBuilder(productions.get(0));
					for(int j = 1;j<productions.size();j++) {
						sb.append("<==>"+productions.get(j));
					}
					jsonObj.put("production", sb.toString());
				} else {
					jsonObj.put("production", "");
				}
			} catch(Exception e) {
				e.printStackTrace();
				log.info("getProductions Construct json data wrong!");
				return ResponseEntity.badRequest().header("Json","Construct json data wrong!").body(null);
			}
			jsonArr.put(jsonObj);
		}
		
		return ResponseEntity.ok().body(jsonArr.toString());
	}
	
	/**
	 * 存储老师选择的优秀作品信息
	 * @param ids
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/saveExcellentProductions", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> saveProductions(@RequestBody List<Long> ids){
		
		excellentProductionRepository.deleteAll();
		for(Long id : ids) {
			QuestionAnswer qa = questionAnswerRepository.findOne(id);
						
			ExcellentProduction ep = new ExcellentProduction();
			ep.setQuestionAnswer(qa);
			excellentProductionRepository.save(ep);
		}
		
		return ResponseEntity.ok().body(null);		
	}
	
	@RequestMapping(value = "/getExcellentProductions", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> getExcellentProductions(){
		List<ExcellentProduction> epList = excellentProductionRepository.findAll();
		JSONArray jsonArr = new JSONArray();
		for(ExcellentProduction ep : epList) {
			QuestionAnswer questionAnswer = ep.getQuestionAnswer();
			if(questionAnswer != null) {
				JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("studentName", questionAnswer.getStudent().getUser().getLogin());
				} catch(Exception e) {
					e.printStackTrace();
					log.info("getExcellentProductions Construct json data wrong!");
					return ResponseEntity.badRequest().header("Json","Construct json data wrong!").body(null);
				}
				String temp_url = questionAnswer.getAnswer();
				String[] urls = temp_url.split("<==>");
				ArrayList<String> productions = new ArrayList<String>();
				// 处理作品图片
				for(int i=1 ; i<urls.length;i++) {
					try {
						File file = new File(urls[i]);
						FileInputStream inputStream = new FileInputStream(file);
		
						byte[] bytes = new byte[inputStream.available()];
		
						inputStream.read(bytes, 0, inputStream.available());
						inputStream.close();				
						String data = Base64.encodeBase64String(bytes);
						
						productions.add(data);
					} catch(Exception e) {
						e.printStackTrace();
						log.info("getExcellentProductions 图片读取错误");
						continue;
					}
				}
				try {
					if(productions.size() > 0) {
						StringBuilder sb = new StringBuilder(productions.get(0));
						for(int j = 1;j<productions.size();j++) {
							sb.append("<==>"+productions.get(j));
						}
						jsonObj.put("production", sb.toString());
					} else {
						jsonObj.put("production", "");
					}
				} catch(Exception e) {
					e.printStackTrace();
					log.info("getProductions Construct json data wrong!");
					return ResponseEntity.badRequest().header("Json","Construct json data wrong!").body(null);
				}
				jsonArr.put(jsonObj);
			}			
		}
		return ResponseEntity.ok().body(jsonArr.toString());
	}
}
