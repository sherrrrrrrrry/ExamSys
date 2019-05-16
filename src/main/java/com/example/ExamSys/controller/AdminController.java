package com.example.ExamSys.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExamSys.dao.StudentRepository;
import com.example.ExamSys.dao.TeacherRepository;
import com.example.ExamSys.dao.UserRepository;
import com.example.ExamSys.domain.Student;
import com.example.ExamSys.domain.User;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private final Logger log = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	TeacherRepository teacherRepository;

	/**
	 * 获取未激活的账户列表
	 * @return
	 */
	@RequestMapping(value = "/getUnactivatedList", method = RequestMethod.POST)
	public ResponseEntity<JSONArray> getUnactivatedList(){
		
		log.info("正在获取未激活账户列表");
		List<User> userSet = userRepository.findAllByEnabledFalse();
		log.info("获取未激活账户列表成功");
		JSONArray jsonArr = new JSONArray();
		for(User user : userSet) {
			try {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("login", user.getLogin());
				jsonObj.put("email", user.getEmail());
				jsonObj.put("phoneNumber", user.getPhoneNumber());
				jsonArr.put(jsonObj);
			} catch(Exception e) {
				e.printStackTrace();
				log.info(e.toString());
				return ResponseEntity.badRequest().body(null);
			}
		}
		
		return ResponseEntity.ok().body(jsonArr);
	}
	
	@DeleteMapping(value = "/activate")
	public ResponseEntity<Boolean> activate(@RequestBody List<Long> ids){
		
		log.info("正在激活所选账户");
		try {
			userRepository.setEnabledTrueByIdIn(ids);
		} catch(Exception e) {
			log.info("账户激活失败");
			return ResponseEntity.badRequest().body(false);
		}
		log.info("账户激活完毕");
		
		return ResponseEntity.ok().body(true);
	}
//	
//	@RequestMapping(value = "/getStudentList", method = RequestMethod.POST)
//	public ResponseEntity<JSONArray> getStudentList(){
//		log.info("正在获取学生列表");
//		List<Student> studentList = studentRepository.findAll();
//		log.info("获取学生列表成功");
//		JSONArray jsonArr = new JSONArray();
//		for(Student student : studentList) {
//			try {
//				JSONObject jsonObj = new JSONObject();
//				jsonObj.put("login", student.getLogin());
//				jsonObj.put("email", student.getEmail());
//				jsonObj.put("phoneNumber", student.getPhoneNumber());
//				jsonArr.put(jsonObj);
//			} catch(Exception e) {
//				e.printStackTrace();
//				log.info(e.toString());
//				return ResponseEntity.badRequest().body(null);
//			}
//		}
//	}
}
