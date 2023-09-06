package com.example.laos.newservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenerateAndDownloadTankInputExcel {

    public static String FilePath = "D:\\dev_etc\\tank\\newfile";
//    public static String FilePath = "D:\\dev_etc\\tank\\NN2023";

    public static void generateAndDownloadExcel(HttpServletResponse response)  {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FilePath));
            Workbook workbook = new XSSFWorkbook() ;

            Sheet sheet = workbook.createSheet("TankData");
            Row headerRow = sheet.createRow(0);

            String[] header = {"일자", "강우", "유출량"};
            for (int i = 0; i < header.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(header[i]);
            }

            String line;
            boolean startParsing = false;
            int rowNumber = 1;

            int n = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                n++;
                if(n == 29) startParsing = true;
                if(startParsing){

//                    // 10칸씩 잘라서 배열로 만들고 값을 trim 해서 입력하는 방법
//                    currentData.addAll(Arrays.asList(line.split("\\s+")));
//                    if (currentData.size() >= 10) {
//                        Row row = sheet.createRow(rowNumber++);
//                        for (int i = 0; i < currentData.size(); i++) {
//                            Cell cell = row.createCell(i);
//                            cell.setCellValue(currentData.get(i));
//                        }
//                        currentData.clear();
//                    }

                    String[] values = line.split("\\s+");
                    Row row = sheet.createRow(rowNumber++);
                    for (int i = 0; i < values.length; i++) {
                        Cell cell = row.createCell(i);
                        cell.setCellValue(values[i]);
                    }
                }
            }

            String excelFilePath = "tank_data.xlsx";

            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
//                workbook.write(outputStream);
//                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//                response.setHeader("Content-Disposition", "attachment; filename=tank_data.xlsx");

                workbook.write(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            workbook.close();

            // 다운로드 코드 추가
            try {
                File file = new File(excelFilePath);
                FileInputStream fis = new FileInputStream(file);

                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());

                ServletOutputStream out = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                fis.close();
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Files.deleteIfExists(Paths.get(excelFilePath));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

