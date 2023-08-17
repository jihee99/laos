package com.example.laos.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequiredArgsConstructor()
public class TankController {

    @GetMapping("/")
    public ModelAndView tankPage(){
        ModelAndView mav = new ModelAndView("tank");
        return mav;
    }
}
