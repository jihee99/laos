package com.example.laos.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
public class CreateNewHMSInputFile {
    private static String OriginFilePath = "D:\\dev_etc\\";
    private static String TemplateFilePath = "D:\\dev_etc\\hms\\templates\\";
    private static String CreateInputFilePath = "D:\\dev_etc\\hms\\input\\";
//    private static String FilePath = "D:\\dev_etc\\hms\\";

    public static void main(String[] args) throws IOException {
        String originFileName = "Control_1.control";

        Path path = Paths.get(OriginFilePath, "hms", originFileName);

        List<String> lines = Files.readAllLines(path);
        StringBuilder templateContent = new StringBuilder();

        int idx = 0;
        while(idx<lines.size()){
            if (lines.get(idx).trim().startsWith("Start Date:")) {
                templateContent.append("     Start Date: $${StartDate}\n");
            } else if (lines.get(idx).trim().startsWith("Start Time:")) {
                templateContent.append("     Start Time: $${StartTime}\n");
            } else if (lines.get(idx).trim().startsWith("End Date:")) {
                templateContent.append("     End Date: $${EndDate}\n");
            } else if (lines.get(idx).trim().startsWith("End Time:")) {
                templateContent.append("     End Time: $${EndTime}\n");
            } else {
                templateContent.append(lines.get(idx)).append("\n");
            }
            idx++;
        }

        int lastDotIndex = originFileName.lastIndexOf(".");

        String TemplateFileName = originFileName.substring(0, lastDotIndex);
        String extension = originFileName.substring(lastDotIndex + 1);

        TemplateFilePath += TemplateFileName+".template";

        // Create directory if it doesn't exist
        Path templatePath = Paths.get(TemplateFilePath);
        if (!Files.exists(templatePath.getParent())) {
            Files.createDirectories(templatePath.getParent());
        }

        FileWriter fileWriter = new FileWriter(TemplateFilePath);
        fileWriter.write(templateContent.toString());

        createNewInputControlFile(TemplateFileName, extension);

        fileWriter.close();

    }

    public static void createNewInputControlFile(String tfn, String extension) throws IOException {
//        Path path3 = Paths.get(TemplateFilePath + tfn + ".template");

        String templateFileName = tfn+".template";




        System.out.println(TemplateFilePath);
        Path path = Paths.get(TemplateFilePath);

        List<String> lines = Files.readAllLines(path);

        log.info("{}", lines.size());
        StringBuilder inputContent = new StringBuilder();
        int idx = 0;

        while(idx<lines.size()){
            modifyLine(lines.get(idx));
            inputContent.append(modifyLine(lines.get(idx))).append("\n");
            idx++;
        }

        CreateInputFilePath += tfn +"."+ extension;
        log.info("{}", CreateInputFilePath);

        // Create directory if it doesn't exist
        Path inputPath = Paths.get(CreateInputFilePath);
        if (!Files.exists(inputPath.getParent())) {
            Files.createDirectories(inputPath.getParent());
        }

        FileWriter fileWriter = new FileWriter(CreateInputFilePath);
        fileWriter.write(inputContent.toString());
        fileWriter.close();

        log.info("{}","Control template file created successfully.");


    }

    private static String modifyLine(String line) {

        // 현재 날짜 계산
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy,HH:mm", Locale.ENGLISH);
        Date currentDate = new Date();
        String formattedStartDate = dateFormat.format(currentDate);

        // 현재로부터 10일 뒤의 날짜 계산
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 10);

        Date tenDaysLater = calendar.getTime();
        String formattedEndDate = dateFormat.format(tenDaysLater);

        if (line.contains("$${StartDate}")) {
            return line.replace("$${StartDate}", formattedStartDate.split(",")[0]); // "Start Date" 라인 수정
        } else if (line.contains("$${StartTime}")) {
            return line.replace("$${StartTime}", formattedStartDate.split(",")[1]);
        } else if (line.contains("$${EndDate}")) {
            return line.replace("$${EndDate}", formattedEndDate.split(",")[0]);
        } else if (line.contains("$${EndTime}")) {
            return line.replace("$${EndDate}", formattedEndDate.split(",")[1]);
        } else {
            return line;
        }
    }

}
