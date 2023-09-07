package com.example.laos.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.laos.newservice.GenerateAndDownloadTankInputExcel;
import com.example.laos.newservice.createUploadInputFile;
import com.example.laos.vo.EstimateSummaryVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tank")
public class TankController {

	@GetMapping("/generateAndDownloadExcel")
	public void generateAndDownloadExcel(HttpServletResponse response) {
		GenerateAndDownloadTankInputExcel.generateAndDownloadExcel(response);
	}

	@PostMapping("/runTank")
	public EstimateSummaryVo runTank(@RequestBody MultipartFile file) {
		if (!file.isEmpty()) {
			EstimateSummaryVo es = createUploadInputFile.createUploadInputFile(file);
			return es;
		}
		return null;
	}

}
