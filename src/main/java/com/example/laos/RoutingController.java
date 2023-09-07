package com.example.laos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RoutingController {
	@GetMapping("/tank")
	public ModelAndView tankPage() {
		ModelAndView modelAndView = new ModelAndView("tank");
		return modelAndView;
	}

	@GetMapping("/dmh")
	public ModelAndView dmhPage(){
		ModelAndView modelAndView = new ModelAndView("dmh");
		return modelAndView;
	}

}
