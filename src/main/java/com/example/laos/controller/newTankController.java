package com.example.laos.controller;

import com.example.laos.newservice.GenerateAndDownloadTankInputExcel;
import com.example.laos.newservice.createUploadInputFile;
import com.example.laos.vo.EstimateSummaryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequiredArgsConstructor()
public class newTankController {

//    private static String ResultPath = "D:\\dev_etc\\";
//    private static String TemplateFilePath = "D:\\dev_etc\\tank\\templates\\";
//    private static String ModelPath = "D:\\dev_etc\\tank\\SMTankSim.exe";

    @GetMapping("/")
    public ModelAndView tankPage() {
        ModelAndView mav = new ModelAndView("tank");
        return mav;
    }

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
