package com.example.ExamSys.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ExamSys.config.Constants;
import com.example.ExamSys.dao.ProductionRepository;
import com.example.ExamSys.dao.StudentRepository;
import com.example.ExamSys.dao.TeacherRepository;
import com.example.ExamSys.dao.UserRepository;
import com.example.ExamSys.domain.Production;
import com.example.ExamSys.domain.User;

@Service
public class ProductionService {
	private final Logger logger = LoggerFactory.getLogger(ProductionService.class);
	
	private static String studentProductionParentPath = Constants.STUDENT_PRODUCTION_PATH;
	
	private static String studentPersonalPhotoPath = Constants.STUDENT_PERSONAL_PHOTO_PATH;
	
	private static String teacherPersonalPhotoPath = Constants.TEACHER_PERSONAL_PHOTO_PATH;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductionRepository productionRepository;
	
	public String upLoadStudentProduction(String userName, File file) {
	/**
	 * 保存作品，参数：用户名，文件
	 */
		Long userId = studentRepository.findUserIdByLogin(userName);
		
		String productionName = createProductionName(userId);
		String url = saveProductionInLocalFS(studentProductionParentPath, file, productionName);
		
		if(url == null) {
			logger.error("user:{} Failed to save the production Name:{}", userId, file.getName());
			return null;
		}
		
//		User user = studentRepository.findUserByLogin(userName);
//		Production production = new Production();
//		production.setUser(user);
//		production.setProductionUrl(url);
//		productionRepository.save(production);
		logger.info("user: {} save the production successfully Name: {}", userId, file.getName());
		return url;
	}
	
	public boolean upLoadStudentProduction(Long stuid, String name, File file) {
		String oldProductionUrl = null;
		Long userId = studentRepository.findUserIdById(stuid);
		
		if((oldProductionUrl = productionRepository.findOneByUserIdAndName(userId, name)) != null) {
			File oldFile = new File(oldProductionUrl);
			oldFile.delete();
			logger.info("user: {} delete the old production successfully Name: {}", userId, oldFile.getName());
			productionRepository.deleteByUserIdAndName(userId, name);
		}
		
		String productionName = createProductionName(stuid, file);
		String url = saveProductionInLocalFS(studentProductionParentPath, file, productionName);
		if(url == null) {
			logger.error("user:{} Failed to save the production Name:{}", userId, file.getName());
			return false;
		}
		
		User user = studentRepository.findUserById(stuid);
		Production production = new Production();
		production.setName(name);
		production.setProductionUrl(url);
		production.setUser(user);
		productionRepository.save(production);
		logger.info("user: {} save the production successfully Name: {}", userId, file.getName());
		return true;
	}
	
	/*
	 * 将 production 保存到本地磁盘上，并返回存储路径
	 */
	private String saveProductionInLocalFS(String parentProductionPath, File file, String productionName) {
		
		String productionPath = null;
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] temp = new byte[inputStream.available()];
			File parentDir = new File(parentProductionPath);
			if(!parentDir.exists())
				parentDir.mkdirs();
			
			productionPath = parentDir.getPath() + File.separator + productionName;
			File out = new File(productionPath);
			if(!out.exists())
				out.createNewFile();
			OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(out));
			
			int len = 0;
			while((len = inputStream.read(temp)) != -1) {
				outputStream.write(temp, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
		} catch(FileNotFoundException exception) {
			logger.error("save production error: The file not found");
			exception.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return productionPath;
	}
	/*
	 * 创建作品文件名称，根据用户id和时间戳
	 */
	private String createProductionName(Long userId) {
		StringBuilder productionName = new StringBuilder();
		
		if(userId != null)
			productionName.append(userId);
		else productionName.append("unknownuser");
		productionName.append("_");
		
		productionName.append(System.currentTimeMillis());
		return productionName.toString();
	}
	/*
	 * 创建作品文件名称，根据用户id和文件名
	 */
	private String createProductionName(Long stuid, File file) {
		StringBuilder productionName = new StringBuilder();
		
		Long userId = studentRepository.findUserIdById(stuid);
		if(userId != null)
			productionName.append(userId);
		else productionName.append("unknownuser");
		productionName.append("_");
		
		productionName.append(file.getName());
		return productionName.toString();
	}
	/*
	 * 上传个人照
	 */
	public boolean upLoadPersonalPhoto(String login, File file, String userType) {
		String oldPhotoUrl = null;
		Long userId = userRepository.findIdByLogin(login);
		
		if(userType.equals("Student")) {
			if((oldPhotoUrl = studentRepository.findPhotoUrlByLogin(login)) != null) {
				File oldFile = new File(oldPhotoUrl);
				oldFile.delete();
				logger.info("user: {} delete the old production successfully Name: {}", userId, oldFile.getName());
				studentRepository.updatePhotoUrlByLogin("", login);
			}
			String photoName = login + "_" + file.getName();
			
			String url = saveProductionInLocalFS(studentPersonalPhotoPath, file, photoName);

			if(url == null) {
				logger.error("user:{} Failed to save the production Name:{}", userId, file.getName());
				return false;
			}
			studentRepository.updatePhotoUrlByLogin(url, login);
			logger.info("user: {} save the production successfully Name: {}", userId, file.getName());
			return true;
		} else if(userType.equals("Teacher")) {
			if((oldPhotoUrl = teacherRepository.findPhotoUrlByLogin(login)) != null) {
				File oldFile = new File(oldPhotoUrl);
				oldFile.delete();
				logger.info("user: {} delete the old production successfully Name: {}", userId, oldFile.getName());
				teacherRepository.updatePhotoUrlByLogin("", login);
			}
			String photoName = login + "_" + file.getName();
			
			String url = saveProductionInLocalFS(teacherPersonalPhotoPath, file, photoName);

			if(url == null) {
				logger.error("user:{} Failed to save the production Name:{}", userId, file.getName());
				return false;
			}
			teacherRepository.updatePhotoUrlByLogin(url, login);
			logger.info("user: {} save the production successfully Name: {}", userId, file.getName());
			return true;
		}
		return false;
	}
}
