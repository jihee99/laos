package com.example.laos.newservice;

import com.example.laos.vo.EstimateInflowVo;
import com.example.laos.vo.EstimateSummaryVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class createUploadInputFile {

    private static String ResultPath = "D:\\dev_etc\\";
    private static String TemplateFilePath = "D:\\dev_etc\\tank\\templates\\";
    private static String ModelPath = "D:\\dev_etc\\tank\\SMTankSim.exe";

    public static EstimateSummaryVo createUploadInputFile(MultipartFile file){
        // 업로드된 파일 처리 로직 작성
        // 입력 파일 생성용 빌더
        try {
            // 업로드된 파일 처리 로직 작성
            // 입력 파일 생성용 빌더
            StringBuilder tankContent = new StringBuilder();

            // 파라미터 파일 읽기
            String tmplPath = TemplateFilePath + "dc0790_param";
//                String tmplPath = TemplateFilePath + "NN2023_param";
            Path path = Paths.get(tmplPath);
            String fileName;

            try (BufferedReader reader = new BufferedReader(new FileReader(tmplPath))) {
                fileName = reader.readLine();
            }
            System.out.println("Read filename:" + fileName);

            // 엑셀 파일 읽기
            List<List<String>> excelData = readExcel(file.getInputStream());

            // 시작일과 종료일 구하기
            String startDate = excelData.get(1).get(0);
            String endDate = excelData.get(excelData.size() - 1).get(0);

            List<String> lines = Files.readAllLines(path);
            int idx = 0;
            while(idx<lines.size()){
                if (lines.get(idx).contains("$${StartDate}")) {
                    lines.set(idx, lines.get(idx).replace("$${StartDate}", formatDate(startDate)));
                }
                if (lines.get(idx).contains("$${EndDate}")) {
                    lines.set(idx, lines.get(idx).replace("$${EndDate}", formatDate(endDate)));
                }

                tankContent.append(lines.get(idx)).append("\n");
                idx++;
            }

            for(int i=1; i<excelData.size(); i++){
                tankContent.append(formatData(String.valueOf(excelData.get(i)))).append("\n");
            }

            // 새 파일 생성 및 내용 작성
            String newFilePath = "D:\\dev_etc\\tank\\" + fileName;
            try (FileWriter writer = new FileWriter(newFilePath)) {
                writer.write(tankContent.toString());
                System.out.println("Success create Input file.");

                String targetPath = "D:\\dev_etc\\tank\\" + fileName.trim();
                Path source = Paths.get(newFilePath);
                Path target = Paths.get(targetPath);
                Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("파일을 원하는 경로로 이동했습니다.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 모델 실행
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", ModelPath);

            builder.directory(new File("D:\\dev_etc\\tank\\"));
            builder.redirectError(ProcessBuilder.Redirect.INHERIT);
            builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            Process proc = builder.start();

            PrintWriter writer = new PrintWriter(proc.getOutputStream());
            writer.write(fileName.trim() + "\n");
            writer.flush();


            int procResult = proc.waitFor();
//                readEstimateInflowModelResult(fileName);

            if(procResult == 0) { //성공
                System.out.println("Model execution successful.");
            } else {
                System.out.println("Model execution failed.");
            }

            // 모델 결과 파일 읽어오기
            EstimateSummaryVo es = readEstimateInflowModelResult(fileName);

//                // 모델 실행이 완료될 때까지 대기 (필요에 따라 적절한 대기 시간 설정)
//                if (!proc.waitFor(1, TimeUnit.MINUTES)) {
//                    // 실행이 1분 안에 완료되지 않으면 timeout 처리
//                    proc.destroy();
//                    throw new RuntimeException("Model execution timeout.");
//                }
//
//                // 모델 실행이 성공적으로 종료되었는지 확인
//                int procResult = proc.exitValue();
//                if (procResult == 0) {
//                    System.out.println("Model execution successful.");
//                } else {
//                    System.out.println("Model execution failed with exit code: " + procResult);
//                }

            // 처리 완료 후 리턴
            return es;
//                return "success"; // 성공한 경우 success 페이지를 반환하거나 다른 작업 수행
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 에러 발생 시 error 페이지를 반환하거나 다른 처리 수행
//                return "error"; // 에러 발생 시 error 페이지를 반환하거나 다른 처리 수행
        }
    }

    private static List<List<String>> readExcel(InputStream inputStream) throws IOException {
        List<List<String>> excelData = new ArrayList<>();

        System.out.println(inputStream);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            List<String> rowData = new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                rowData.add(cell.getStringCellValue()); // 각 셀의 값을 문자열로 읽음
            }
            excelData.add(rowData);
        }

        workbook.close();

        return excelData;
    }

    public static String formatData(String input) {
        String[] parts = input.replace("[", "").replace("]", "").split(", ");
        return String.format("%10s%10s%10s%10s", parts[0], parts[1], " ", parts[2]);
    }


    public static String formatDate(String date){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date parsedDate = null;
        try {
            parsedDate = inputFormat.parse(date);
            System.out.println("$$#$");
            System.out.println(parsedDate);
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
//            throw new RuntimeException(e);
            return null;
        }
    }

    public static EstimateSummaryVo readEstimateInflowModelResult(String fileName) throws IOException {
        EstimateSummaryVo es = new EstimateSummaryVo();
        try {

            System.out.println(ResultPath+"tank//" + fileName+".out");
            Path path = Paths.get(ResultPath, "tank", fileName+".out");
            List<String> lines = Files.readAllLines(path);

            int idx = 25;

//            ArrayList<TankResultData> arr = new ArrayList<>();
            ArrayList<EstimateInflowVo> eilist = new ArrayList<>();
            String uuid = UUID.randomUUID().toString();
            Boolean startParsing = true;

            while (idx< lines.size()) {
                String[] ls = lines.get(idx).replaceAll("\\s\\s+", "Q").split("Q");
                EstimateInflowVo eif = new EstimateInflowVo();

                eif.setDate(ls[0].replaceAll("\\s+", ""));
                eif.setRMm(ls[1].equals("**********") ? "0.0" : ls[1] );
                eif.setQoCms(ls[2].startsWith("*****") ? "0.0" : ls[2]);
                eif.setQsCms(ls[3].startsWith("*****") ? "0.0" : ls[3]);

                eilist.add(eif);
                idx++;

                if(idx < lines.size() && lines.get(idx).contains("-----")){
                    idx += 2;
                    break;
                }
            }

            System.out.println(idx);
            es.setRealRainfall(lines.get(idx).trim().split("=")[1].startsWith("*****") ? "0.00" : lines.get(idx).trim().split("=")[1].trim());
            es.setObservedFlowDept(lines.get(idx+1).trim().split("=")[1].startsWith("*****") ? "0.00" : lines.get(idx+1).trim().split("=")[1].trim());
            es.setComputedFlowDept(lines.get(idx+2).trim().split("=")[1].startsWith("*****") ? "0.00" : lines.get(idx+2).trim().split("=")[1].trim());
            es.setEvapotranspiration(lines.get(idx+3).trim().split("=")[1].startsWith("*****") ? "0.00" : lines.get(idx+3).trim().split("=")[1].trim());

            // ratio 값이 Obs. Ratio /  Com. Ratio 두 개로 분리
            // Obs. Ratio = 0.00 -> Com. Ratio 사용
            es.setRatio(lines.get(idx+6).trim().split("=")[1].trim().startsWith("*****") ? "0.00" : lines.get(idx+6).trim().split("=")[1].trim());

            idx += 33;
            es.setObsMean(lines.get(idx).trim().split("=")[1].startsWith("*****") ? "0.000" : lines.get(idx++).trim().split("=")[1].trim());
            es.setObsSdev(lines.get(idx).trim().split("=")[1].startsWith("*****") ? "0.000" : lines.get(idx++).trim().split("=")[1].trim());
            es.setSimMean(lines.get(idx).trim().split("=")[1].startsWith("*****") ? "0.000" : lines.get(idx++).trim().split("=")[1].trim());
            es.setSimSdev(lines.get(idx).trim().split("=")[1].trim().startsWith("*****") ? "0.000" : lines.get(idx).split("=")[1].trim());

            es.setInflows(eilist);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return es;
    }
}
