package com.example.laos.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.laos.service.FardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/fard")
public class FardController {

	private final FardService fardService;

	@GetMapping("/run")
	public ResponseEntity<Object> downloadFile(HttpServletResponse response) throws IOException {

		ArrayList<Map<String,Object>> list = fardService.getFardInputDataList();
		String fileName = fardService.refiningFardInputData(list);
		fardService.executeFardModel(fileName);
		return null;
	}

}