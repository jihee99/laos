package com.example.laos.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

@Slf4j
public class InsertTankData {

    public static String DBURL = "jdbc:log4jdbc:postgresql://localhost:5432/laos_test";
    public static String DBUSER = "postgres";
    public static String DBPWD = "postgres00";

//    public static String FilePath = "D:\\SON\\2023\\00.DATA\\file";
    public static String FilePath = "D:\\SON\\2023\\00.DATA\\cs\\file";

    public static void main(String[] args){
        try{
            // 파일 읽기
            BufferedReader reader = new BufferedReader(new FileReader(FilePath));
            String line;
            boolean startParsing = false;

            String basin_cd = null;

            // PostgreSQL DB 연결
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(DBURL, DBUSER, DBPWD);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO INPUT_TANK_DATA(basin_cd, obs_date, rainfall, value1, evaporation) VALUES (?, ?, ?, ?, ?)");

            int n = 0;
            while ((line = reader.readLine()) != null) {
                if(n == 0) basin_cd = line.trim();
                n++;

                // 데이터 파싱 시작 위치 찾기
                if (n==29) startParsing = true;
//                if (line.trim().split("\\s+")[0].equals("2007-01-01")) startParsing = true;
                if(startParsing){

                    log.info("{}", line);
//                    String[] data = line.split("\\s+");
//                    System.out.print(data[0] + "     ");
//                    System.out.print(data[1]+ "     ");
//                    System.out.print(data[2]+ "     ");
//                    System.out.print(data[3]+ "     ");
//                    System.out.println();

                    // 데이터 파싱
                    String[] data = line.split("\\s+");
//                    String dateString = data[0];
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    Date utilDate = sdf.parse(dateString);
                    Date date = Date.valueOf(data[0]);
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    Date date = (Date) sdf.parse(data[0]);
//                    Date date = Date.valueOf("to_date('"+data[0]+"', 'yyyy-mm-dd')");

                    String value1 = data[1];
                    String value2 = data[2];
                    String value3 = data[3];



                    // 데이터 DB에 저장
                    pstmt.setString(1, basin_cd);
                    pstmt.setDate(2, date);
//                    pstmt.setDate(2, date);
                    pstmt.setString(3, value1);
                    pstmt.setString(4, value2);
                    pstmt.setString(5, value3);

//                    // 코드 실행
                    pstmt.executeUpdate();
                }

            }

            // 자원 해제
            pstmt.close();
            conn.close();
            reader.close();

            System.out.println("데이터가 성공적으로 DB에 저장되었습니다.");

        }catch (IOException | ClassNotFoundException | SQLException  e) {
            throw new RuntimeException(e);
        }

    }
}
