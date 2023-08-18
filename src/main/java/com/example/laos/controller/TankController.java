package com.example.laos.controller;

import com.example.laos.util.GenerateAndDownloadTankInputExcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
@RequiredArgsConstructor()
public class TankController {

    @GetMapping("/")
    public ModelAndView tankPage(){
        ModelAndView mav = new ModelAndView("tank");
        return mav;
    }

    @GetMapping("/generateAndDownloadExcel")
    public void generateAndDownloadExcel(HttpServletResponse response) {
        GenerateAndDownloadTankInputExcel.generateAndDownloadExcel(response);
    }






}
