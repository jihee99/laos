package com.example.laos.controller;

import com.example.laos.common.service.CommonService;
import com.example.laos.common.util.CreateInputDataFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        CreateInputDataFile.dataToFile(data.get("basicData"), data.get("inputData"));

        // 생성한 파일을 읽어서 byte 배열로 변환
        byte[] fileBytes;
//        try {
//            fileBytes = Files.readAllBytes(Paths.get("input.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        // 파일 다운로드를 위한 HTTP 응답 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "input.txt");

        return null;
//        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

}

