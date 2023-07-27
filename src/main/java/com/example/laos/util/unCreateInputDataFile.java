//package com.example.laos.common.util;
//
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//public class unCreateInputDataFile {
//
//    public static ResponseEntity<FileSystemResource> downloadInputDataFile(Map<String, Object> data) throws IOException, IOException {
//        String fileName = "input.file";
//
//        try (FileWriter writer = new FileWriter(fileName)) {
//            // Write basicData to the file
//            Map<String, String> basicData = (Map<String, String>) data.get("basicData");
//            for (Map.Entry<String, String> entry : basicData.entrySet()) {
//                writer.write(entry.getKey() + "=" + entry.getValue() + System.lineSeparator());
//            }
//            writer.write(System.lineSeparator());
//
//            // Write inputData to the file
//            List<Map<String, String>> inputData = (List<Map<String, String>>) data.get("inputData");
//            for (Map<String, String> entry : inputData) {
//                for (Map.Entry<String, String> inputEntry : entry.entrySet()) {
//                    writer.write(inputEntry.getKey() + "=" + inputEntry.getValue() + " ");
//                }
//                writer.write(System.lineSeparator());
//            }
//        }
//
//        System.out.println("Data has been written to " + fileName);
//
//        // 파일을 다운로드하는 ResponseEntity 객체 생성
//        FileSystemResource fileResource = new FileSystemResource(fileName);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDispositionFormData("attachment", fileName);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(fileResource);
//
//    }
//
//
//
////    public static void DataToFile(Map<String, Object> data) {
////        String fileName = "input.txt";
////
////        try (FileWriter writer = new FileWriter(fileName)) {
////            // Write basicData to the file
////            Map<String, String> basicData = (Map<String, String>) data.get("basicData");
////            for (Map.Entry<String, String> entry : basicData.entrySet()) {
////                writer.write(entry.getKey() + "=" + entry.getValue() + System.lineSeparator());
////            }
////            writer.write(System.lineSeparator());
////
////            // Write inputData to the file
////            List<Map<String, String>> inputData = (List<Map<String, String>>) data.get("inputData");
////            for (Map<String, String> entry : inputData) {
////                for (Map.Entry<String, String> inputEntry : entry.entrySet()) {
////                    writer.write(inputEntry.getKey() + "=" + inputEntry.getValue() + " ");
////                }
////                writer.write(System.lineSeparator());
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////        System.out.println("Data has been written to " + fileName);
////
////    }
////
////    public static ResponseEntity<FileSystemResource> downloadInputDataFile(Map<String, Object> data) {
////    }
//}
