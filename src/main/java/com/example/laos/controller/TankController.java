package com.example.laos.controller;

import com.example.laos.util.GenerateAndDownloadTankInputExcel;
import com.example.laos.vo.EstimateInflowVo;
import com.example.laos.vo.TankResultData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@RequiredArgsConstructor()
public class TankController {

    private static String ResultPath = "D:\\dev_etc\\";
    private static String TemplateFilePath = "D:\\dev_etc\\tank\\templates\\";
    private static String ModelPath = "D:\\dev_etc\\tank\\SMTankSim.exe";
    @GetMapping("/")
    public ModelAndView tankPage(){
        ModelAndView mav = new ModelAndView("tank");
        return mav;
    }

    @GetMapping("/generateAndDownloadExcel")
    public void generateAndDownloadExcel(HttpServletResponse response) {
        GenerateAndDownloadTankInputExcel.generateAndDownloadExcel(response);
    }


    @PostMapping("/runTank")
    public String runTank(@RequestBody MultipartFile file) {
//    public String runTank(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 업로드된 파일 처리 로직 작성

                // 입력 파일 생성용 빌더
                StringBuilder tankContent = new StringBuilder();
                
                // 파라미터 파일 읽기
                String tmplPath = TemplateFilePath + "dc0790_parameter";
                Path path = Paths.get(tmplPath);
                String fileName = path.getFileName().toString().split("_")[0];

                // 엑셀 파일 읽기
                List<List<String>> excelData = readExcel(file.getInputStream());

                // 시작일과 종료일 구하기
                String startDate = excelData.get(1).get(0);
                String endDate = excelData.get(excelData.size() - 1).get(0);

                System.out.println();
                System.out.println(startDate + "            " + endDate);
                System.out.println();
                List<String> lines = Files.readAllLines(path);
                int idx = 0;
                while(idx<lines.size()){
                    if (lines.get(idx).contains("$${StartDate}")) {
                        lines.set(idx, lines.get(idx).replace("$${StartDate}", formatDate(startDate))); // 수정된 부분
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

//                int lineCount=0;
//                for(List<String> str : excelData){
//                    if(lineCount>0){
//                        tankContent.append(str);
//                    }
//                    lineCount++;
//                }

                // 새 파일 생성 및 내용 작성
                String newFilePath = "D:/dev_etc/tank/" + fileName;
                try (FileWriter writer = new FileWriter(newFilePath)) {
                    writer.write(tankContent.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 모델 실행
                ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", ModelPath);

                builder.directory(new File("D:\\dev_etc\\tank\\"));
                builder.redirectError(ProcessBuilder.Redirect.INHERIT);
                //builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

                Process proc = builder.start();

                PrintWriter writer = new PrintWriter(proc.getOutputStream());
                writer.write(fileName + "\n");
                writer.flush();


                int procResult = proc.waitFor();

                if(procResult == 0) { //성공
                    System.out.println("S");
                } else {
                    System.out.println("F");
                }

                readEstimateInflowModelResult(fileName);
//
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

                // 결과 파일을 읽어오기
//                ArrayList<TankResultData> simulationData = readSimulationReport("D:\\dev_etc\\", fileName);
                // 처리 완료 후 리턴
                return "success"; // 성공한 경우 success 페이지를 반환하거나 다른 작업 수행
            } catch (Exception e) {
                e.printStackTrace();
                return "error"; // 에러 발생 시 error 페이지를 반환하거나 다른 처리 수행
            }
        }
        return "error"; // 업로드된 파일이 없는 경우 에러 처리
    }

    private List<List<String>> readExcel(InputStream inputStream) throws IOException {
        List<List<String>> excelData = new ArrayList<>();

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
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
//            throw new RuntimeException(e);
            return null;
        }
    }


    public static ArrayList<TankResultData> readEstimateInflowModelResult(String fileName) throws IOException {
        try {

            Path path = Paths.get(ResultPath, "tank", fileName+".out");
            List<String> lines = Files.readAllLines(path);

            int idx = 25;
            int cnt = 0;

//            ArrayList<TankResultData> arr = new ArrayList<>();
            ArrayList<EstimateInflowVo> eilist = new ArrayList<>();
            String uuid = UUID.randomUUID().toString();

            while (idx < lines.size()) {
                String[] ls = lines.get(idx).replaceAll("\\s\\s+", "Q").split("Q");

                EstimateInflowVo ei = new EstimateInflowVo();

                ei.setDate(ls[0].replaceAll("\\s+", ""));
                ei.setRMm(ls[1]);
                ei.setQoCms(ls[2]);
                ei.setQsCms(ls[3]);

                System.out.println(ei.toString());

                eilist.add(ei);

                idx++;
                cnt++;

            }

            TankResultData trd = new TankResultData();

            trd.setResultId(uuid);
            trd.setSumRainfall(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx).trim().split("=")[1]))));
            trd.setSumObsFlowDpth(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 1).trim().split("=")[1]))));
            trd.setSumCompFlowDpth(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 2).trim().split("=")[1]))));
            trd.setSumEvaport(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 3).trim().split("=")[1]))));
            trd.setRunoffRatio(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 4).trim().split("=")[1]))));

            idx += 31;
            trd.setObsMean(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx).trim().split("=")[1]))));
            trd.setObsSdev(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 1).trim().split("=")[1]))));
            trd.setSimMean(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 2).trim().split("=")[1]))));
            trd.setSimSdev(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 3).trim().split("=")[1]))));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
