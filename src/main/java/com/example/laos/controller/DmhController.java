package com.example.laos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.laos.service.DmhFileParsingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dmh")
public class DmhController {

	private final DmhFileParsingService dmhFileParsingService;

	@GetMapping("/dmh-file-parsing/{pathIdx}")
	public String dmhFileParsing(@PathVariable("pathIdx") int pathIdx) {
		dmhFileParsingService.dmhEvaporationFileParsing(pathIdx);
		return "/equipment-list";
	}

}
