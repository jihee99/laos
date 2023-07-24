package com.example.laos.controller;

import com.example.laos.common.service.CommonService;
import com.example.laos.common.vo.ResultMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ViewController {

    private final CommonService commonService;

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

    @PostMapping("/run/{selectedValue}")
    public ResultMap removeExpense(
            @PathVariable(value="selectedValue", required = true) String code
    ) {
        ResultMap result = new ResultMap();
        try {
//            commonService.getTankInputData(code);
            commonService.getTankInputData(code);
            result.setSuccess();
        } catch(Exception e) {
            log.info("ERROR", e);
            result.setFailure();
        }
        return result;
    }
}

