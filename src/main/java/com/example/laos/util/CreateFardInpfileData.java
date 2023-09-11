package com.example.laos.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.laos.vo.FardInputRefinedData;
import com.example.laos.vo.RainfallData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateFardInpfileData{


    public static String createFardInputData(ArrayList<Map<String, Object>> origin) {
//        ArrayList<FardInputRefinedData> fird = new ArrayList<>();

        FardInputRefinedData fird = new FardInputRefinedData();

        Map<String, Object> result = new HashMap<>();
        ArrayList<String> years = new ArrayList<>();
        Map<String, List<Double>> columns = new HashMap<>();

//        List<TimeRainfallData> result = new ArrayList<>();

        ArrayList<String> yr = new ArrayList<>();
//        ArrayList<String> d01List = new ArrayList<>();
//        ArrayList<Double> d02List = new ArrayList<>();
//        ArrayList<Double> d03List = new ArrayList<>();
//        ArrayList<Double> d06List = new ArrayList<>();
//        ArrayList<Double> d12List = new ArrayList<>();
//        ArrayList<Double> d18List = new ArrayList<>();

        for (Map<String, Object> entry : origin) {
            String year = (String) entry.get("yr");
            yr.add((String) entry.get("yr"));

            for (Map.Entry<String, Object> dataEntry : entry.entrySet()) {
                String key = dataEntry.getKey();
//                log.info("{}", dataEntry.getValue());

                if (!key.equals("yr")) {
                    Double value = Double.valueOf(String.valueOf(dataEntry.getValue()));
                    columns.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
                }else{
                    years.add(String.valueOf(dataEntry.getValue()));
                }
            }
//            result.put("yr", years);

            for (Map.Entry<String, List<Double>> columnEntry : columns.entrySet()) {
                String columnName = columnEntry.getKey();
                List<Double> values = columnEntry.getValue();
                result.put(columnName, values);
            }

            result.put("yr",years);

            RainfallData rfd = new RainfallData();


           // return result;
//            TimeRainfallData trd = new TimeRainfallData();
//            RainfallData rd = new RainfallData();
//
//            for (Map.Entry<String, String> dataEntry : entry.entrySet()) {
//                String key = dataEntry.getKey();
//                trd.setTimes();
//                if (!key.equals("yr")) {
//                    Double value = Double.parseDouble(dataEntry.getValue());
//                    rd.getValues().put(key, value);
//                }
//            }


//            TimeRainfallData timeRainfallData = new TimeRainfallData();
//            timeRainfallData.setTimes(year);
//            timeRainfallData.setValues(rd);
//
//            result.add(timeRainfallData);
        }
//        fird.setStartYr(yr.get(0)+"~"+yr.get(yr.size()-1));

//        fird.setDataCnt(cnt);
//        fird.setD01(d01List);
//        fird.setD02(d02List);
//        fird.setD03(d03List);
//        fird.setD06(d06List);
//        fird.setD12(d12List);
//        fird.setD18(d18List);

        return createFardInputFile(fird);

    }



    public static String createFardInputFile(FardInputRefinedData fird){
        String fileName = "INPFILE.DAT";

        try(FileWriter writer = new FileWriter(fileName)){

            writer.write("Test\n");                                 //지점명(가변)
            writer.write(fird.getYr().get(0) + "~" + fird.getYr().get(fird.getYr().size()-1));      //대상자료 시작~종료 년도(가변)
            writer.write("25\n");                                   //강우지속시간수
            writer.write("1\n");                                    //필요한 재현기간에 대한 강우량 구하는 경우 1, 옵션선택 0 -> 6,7line 필요 없음
            writer.write("10\n");                                   //빈도개수

           writeListToWriter(fird.getD01(), writer, "0001");     //강우지속일1 + 깅우량
//            writeListToWriter(fird.getD02(), writer, "0002");     //강우지속일2 + 깅우량
//            writeListToWriter(fird.getD03(), writer, "0003");     //강우지속일3 + 깅우량
//            writeListToWriter(fird.getD06(), writer, "0006");     //강우지속일6 + 깅우량
//            writeListToWriter(fird.getD12(), writer, "0012");     //강우지속일12 + 깅우량
//            writeListToWriter(fird.getD18(), writer, "0018");     //강우지속일18 + 깅우량

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Data has been written to " + fileName);
        return fileName;
    }

    public static void writeListToWriter(ArrayList<Double> list, FileWriter writer, String prefix) throws IOException {
        writer.write(prefix + "\n");
        for (Double value : list) {
            // try {
            //     // 모델 실행
            //     ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", modelPath);
            //
            //     builder.directory(new File("D:\\dev_etc\\fard\\"));
            //     builder.redirectError(ProcessBuilder.Redirect.INHERIT);
            //     //builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            //
            //     Process proc = builder.start();
            //
            //     PrintWriter writer = new PrintWriter(proc.getOutputStream());
            //     writer.write(fileName + "\n");
            //
            //     writer.flush();
            //     writer.close();
            //
            //     //            // 모델 실행이 완료될 때까지 대기 (필요에 따라 적절한 대기 시간 설정)
            //     //            if (!proc.waitFor(1, TimeUnit.MINUTES)) {
            //     //                // 실행이 1분 안에 완료되지 않으면 timeout 처리
            //     //                proc.destroy();
            //     //                throw new RuntimeException("Model execution timeout.");
            //     //            }
            //
            //     // 모델 실행이 성공적으로 종료되었는지 확인
            //     //            int procResult = proc.exitValue();
            //     //            if (procResult == 0) {
            //     //                System.out.println("Model execution successful.");
            //     //            } else {
            //     //                System.out.println("Model execution failed with exit code: " + procResult);
            //     //            }
            //     //            int procResult = proc.waitFor();
            //     //            if(procResult == 0) { //성공
            //     //                System.out.println("S");
            //     //            } else {
            //     //                System.out.println("F");
            //     //
            //
            //     String resultFileName = "(OUT)QUANTILE.DAT";
            //     String resultFilePath = "D:\\dev_etc\\fard\\Result\\" + resultFileName;
            //
            //     boolean resultFileExists = checkFileExists(resultFilePath);
            //     //            boolean processCompleted = proc.waitFor(1, TimeUnit.MINUTES);
            //
            //     if (resultFileExists) {
            //         ReadFardResultData.readFardResultData("D:\\dev_etc\\fard\\", "(OUT)QUANTILE");
            //         // 모델의 표준 출력 스트림으로부터 출력을 읽어옵니다
            //         //                BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            //         //                String line;
            //         //                while ((line = reader.readLine()) != null) {
            //         //                    System.out.println(line);
            //         //                    // 필요에 따라 모델 출력을 처리할 수 있습니다
            //         //                }
            //         //                reader.close();
            //
            //         // 모델 실행 종료값을 확인하여 성공적으로 실행되었는지 판단합니다
            //         //                int exitValue = proc.exitValue();
            //         //                if (exitValue == 0) {
            //         //                    System.out.println("모델 실행 성공.");
            //         ////                    ReadFardResultData.readFardResultData("D:\\dev_etc\\fard\\", "(OUT)QUANTILE");
            //         //                } else {
            //         //                    System.out.println("모델 실행 실패, 종료 코드: " + exitValue);
            //         //                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            //         //                }
            //         System.out.println("모델 실행 성공.");
            //     } else {
            //         // 지정된 타임아웃 내  프로세스가 완료되지 않았을 경우
            //         System.out.println("모델 실행 타임아웃.");
            //         proc.destroy();
            //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            //     }
            // } catch (IOException e) {
            //     e.printStackTrace();
            //     return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            // }
            if(String.valueOf(value).equals("567.0")) writer.write(" " + String.format("%.1f", value+0.1));
            else writer.write(" " + String.format("%.1f", value));
        }
        writer.write("\n");
    }
    String resultFileName = "결과파일.txt";
    String resultFilePath = "D:\\dev_etc\\fard\\" + resultFileName;

    private static boolean checkFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && !file.isDirectory();
    }
}



