package com.example.laos.util;

import com.example.laos.vo.FardInputRefinedData;
import com.example.laos.vo.RainfallData;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


//            return result;
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

//            writeListToWriter(fird.getD01(), writer, "0001");     //강우지속일1 + 깅우량
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
            if(String.valueOf(value).equals("567.0")) writer.write(" " + String.format("%.1f", value+0.1));
            else writer.write(" " + String.format("%.1f", value));
        }
        writer.write("\n");
    }
    String resultFileName = "결과파일.txt";
    String resultFilePath = "D:\\dev_etc\\fard\\" + resultFileName;


}



