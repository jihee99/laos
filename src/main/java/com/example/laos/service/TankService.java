package com.example.laos.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.example.laos.vo.EstimateSummaryVo;

public interface TankService {

	void generateBasinParameterTemplate(String basinName);

	void generateAndDownloadTankInputExcel(HttpServletResponse response);

	void downloadExcelFile(HttpServletResponse response, String excelPath);

	EstimateSummaryVo createUploadInputFile(MultipartFile file);

	List<List<String>> readExcel(InputStream inputStream);

	String formatDate(String date);

	String formatData10(String data);

	String formatData12(String data);

	EstimateSummaryVo readEstimateInflowModelResult(String fileName);

}
