package com.example.laos.util;

import com.example.laos.vo.FardInputRefinedData;
import com.example.laos.vo.RainfallData;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledFardUsingExecutorService {

    public static String DBURL = "jdbc:log4jdbc:postgresql://192.168.0.6:5432/laos";
    public static String DBUSER = "laos";
    public static String DBPWD = "laos00";

    public static String sql = "SELECT " +
            "SUBSTRING(OBS_DT, 1, 4) AS YR, " +
            "MAX(S1) AS D01, " +
            "MAX(S2) AS D02, " +
            "MAX(S3) AS D03, " +
            "MAX(S4) AS D06, " +
            "MAX(S5) AS D12, " +
            "MAX(S6) AS D18 " +
            "FROM" +
            "(" +
            "SELECT " +
            "OBS_DT, " +
            "SUM(D0) AS S1, " +
            "SUM(D0+D1) AS S2, " +
            "SUM(D0+D1+D2) AS S3, " +
            "SUM(D0+D1+D2+D3+D4+D5) AS S4, " +
            "SUM(D0+D1+D2+D3+D4+D5+D6+D7+D8+D9+D10+D11) AS S5, " +
            "SUM(D0+D1+D2+D3+D4+D5+D6+D7+D8+D9+D10+D11+D12+D13+D14+D15+D16+D17) AS S6 " +
            "FROM" +
            " (" +
            "SELECT " +
            "OBS_DT, " +
            "RAINFALL AS D0, " +
            "LEAD(RAINFALL, 1, 0) OVER(ORDER BY OBS_DT) D1, " +
            "LEAD(RAINFALL, 2, 0) OVER(ORDER BY OBS_DT) D2, " +
            "LEAD(RAINFALL, 3, 0) OVER(ORDER BY OBS_DT) D3, " +
            "LEAD(RAINFALL, 4, 0) OVER(ORDER BY OBS_DT) D4, " +
            "LEAD(RAINFALL, 5, 0) OVER(ORDER BY OBS_DT) D5, " +
            "LEAD(RAINFALL, 6, 0) OVER(ORDER BY OBS_DT) D6, " +
            "LEAD(RAINFALL, 7, 0) OVER(ORDER BY OBS_DT) D7, " +
            "LEAD(RAINFALL, 8, 0) OVER(ORDER BY OBS_DT) D8, " +
            "LEAD(RAINFALL, 9, 0) OVER(ORDER BY OBS_DT) D9, " +
            "LEAD(RAINFALL, 10, 0) OVER(ORDER BY OBS_DT) D10, " +
            "LEAD(RAINFALL, 11, 0) OVER(ORDER BY OBS_DT) D11, " +
            "LEAD(RAINFALL, 12, 0) OVER(ORDER BY OBS_DT) D12, " +
            "LEAD(RAINFALL, 13, 0) OVER(ORDER BY OBS_DT) D13, " +
            "LEAD(RAINFALL, 14, 0) OVER(ORDER BY OBS_DT) D14, " +
            "LEAD(RAINFALL, 15, 0) OVER(ORDER BY OBS_DT) D15, " +
            "LEAD(RAINFALL, 16, 0) OVER(ORDER BY OBS_DT) D16, " +
            "LEAD(RAINFALL, 17, 0) OVER(ORDER BY OBS_DT) D17 " +
            "FROM " +
            "TEMP.OBS_AWS_DD " +
            ") A " +
            "GROUP BY " +
            "OBS_DT " +
            ") B " +
            "GROUP BY " +
            "YR " +
            "ORDER BY " +
            "YR ";

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            // 여기에 주기적으로 실행할 작업을 넣기
            System.out.println("Scheduled task executed!");

            try {
                Connection conn = DriverManager.getConnection(DBURL, DBUSER, DBPWD);
                Statement stmt = conn.createStatement();

                ResultSet resultSet = stmt.executeQuery(sql);

                System.out.println(resultSet.toString());

                while(resultSet.next()){
                    // 각 줄의 필드 데이터 읽기
                    String yr = resultSet.getString("YR");
                    double d01 = resultSet.getDouble("D01");
                    double d02 = resultSet.getDouble("D02");
                }



            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

//            String fileName = CreateFardInpfileData.createFardInputData(list);



        };

        // 1초 후부터 60초마다 작업 실행
        scheduler.scheduleAtFixedRate(task, 1, 60, TimeUnit.SECONDS);
    }


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

}


