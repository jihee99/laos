package com.example.laos.util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GenerateTankInputTemplate {

    private static String OriginFilePath = "D:\\dev_etc\\";
    private static String TemplateFilePath = "D:\\dev_etc\\tank\\templates\\";
    public static void main(String[] args){
        String originFileName = "dc0790";

        Path path = Paths.get(OriginFilePath,"tank", originFileName);

        try {
            List<String> lines = Files.readAllLines(path);

            StringBuilder templateContent = new StringBuilder();

            String fileName = "";

            int idx = 0;

            while(idx<28){
                if(idx == 0) fileName = lines.get(idx).trim() + "_parameter";

                if(idx == 2){
                    templateContent.append("$${StartDate}          $${EndDate}").append("\n");
                }else{
                    templateContent.append(lines.get(idx)).append("\n");
                }
                idx++;
            }

//            // 확장자가 있을 경우 해당 방식으로 파일명 설정하기
//            int lastDotIndex = originFileName.lastIndexOf(".");
//            String TemplateFileName = originFileName.substring(0, lastDotIndex);
//            String extension = originFileName.substring(lastDotIndex + 1);
            TemplateFilePath += fileName;

            // Create directory if it doesn't exist
            Path templatePath = Paths.get(TemplateFilePath);
            if (!Files.exists(templatePath.getParent())) {
                Files.createDirectories(templatePath.getParent());
            }


            FileWriter fileWriter = new FileWriter(TemplateFilePath);
            fileWriter.write(templateContent.toString());

            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
