package com.example.laos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.laos.service.CommonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/fard")
public class FardController {

	private final CommonService commonService;

	// @GetMapping("/download/{selectedValue}")
	// public ResponseEntity<byte[]> downloadFile(
	// 	@PathVariable(value="selectedValue", required = true) String code
	// ) throws IOException {
	//
	// 	Map<String, Object> data = commonService.getTankInputData(code);
	//
	// }
}