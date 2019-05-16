package com.example.ExamSys.controller;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ExamSys.service.ProductionService;

@RestController
@RequestMapping("/student")
public class ProductionController {
	
	@Autowired
	ProductionService productionService;
	
	@PostMapping("/exam/{stuid}/uploadproduction")
	public ResponseEntity upLoadProduction(@PathVariable("stuid") Long stuid, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
		boolean bool = false;
		try {
			File upl = File.createTempFile(name + "_", file.getOriginalFilename());
			IOUtils.copy(file.getInputStream(), new FileOutputStream(upl));

			bool = this.productionService.upLoadStudentProduction(stuid, name, upl);
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		if(bool)
			return ResponseEntity.ok().build();
		else
			return ResponseEntity.badRequest().body("upload failed");
	}
	
	@PostMapping("/exam/uploadproductions")
	public ResponseEntity upLoadProductions(@RequestParam("login") String login, @RequestParam("file") MultipartFile file) {
		try {
			File upl = File.createTempFile(login + "_", file.getOriginalFilename());
			IOUtils.copy(file.getInputStream(), new FileOutputStream(upl));

			String url = this.productionService.upLoadStudentProduction(login, upl);
			System.out.println(url);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("upload failed");
		}
		return ResponseEntity.ok().build();
	}
}
