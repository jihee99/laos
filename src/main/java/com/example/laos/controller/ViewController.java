package com.example.laos.controller;

import com.example.laos.service.CommonService;
import com.example.laos.util.CreateInputDataFile;
import com.example.laos.vo.TankBasicInputData;
import com.example.laos.vo.TankInputData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ViewController {

    private final CommonService commonService;

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

//    @GetMapping("/3run/{selectedValue}")
//    public ResultMap removeExpense(
//            @PathVariable(value="selectedValue", required = true) String code
//    ) {
//        ResultMap result = new ResultMap();
//        try {
////            commonService.getTankInputData(code);
////            HashMap data = commonService.getTankInputData(code);
////            CreateInputDataFile.DataToFile(data);
//
//            // 데이터 생성
//            Map<String, Object> data = commonService.getTankInputData(code);
//
//            // 파일 생성 및 다운로드
//            ResponseEntity<FileSystemResource> responseEntity = CreateInputDataFile.dataToFile(data);
//
//
//            // 다운로드를 위한 HttpHeaders 설정
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", "input.txt");
//
//
//
//            result.setSuccess();
//        } catch(Exception e) {
//            log.info("ERROR", e);
//            result.setFailure();
//        }
//        return result;
//    }

    @GetMapping("/download/{selectedValue}")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable(value="selectedValue", required = true) String code
    ) {

        Map<String, Object> data = commonService.getTankInputData(code);
//        Map<String, ArrayList<Map<String,String>>> data = commonService.getTankInputData(code);
        CreateInputDataFile.dataToFile((TankBasicInputData) data.get("basicData"), (ArrayList<TankInputData>) data.get("inputData"));

        // 생성한 파일을 읽어서 byte 배열로 변환
        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(Paths.get("input"));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 파일 다운로드를 위한 HTTP 응답 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("input").build()); // 확장자를 제거합니다.

        headers.setContentDispositionFormData("attachment", "input");

//        return null;
        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

}

