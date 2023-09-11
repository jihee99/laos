package com.example.laos.service;

import java.util.ArrayList;
import java.util.Map;

public interface FardService {
	ArrayList<Map<String,Object>> getFardInputDataList();

	String refiningFardInputData(ArrayList<Map<String, Object>> origin);

	String generateFardInputFile(Map<String, Object> data);

	void executeFardModel(String fileName);


	void readFardResultData(String filePath, String fileName);

}
