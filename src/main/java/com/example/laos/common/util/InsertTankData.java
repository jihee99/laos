package com.example.laos.common.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTankData {

    public static String DBURL = "jdbc:log4jdbc:postgresql://localhost:5432/laos_test";
    public static String DBUSER = "postgres";
    public static String DBPWD = "postgres00";

    public static String FilePath = "D:\\SON\\2023\\00.DATA\\dc0790";

    public static void main(String[] args){
        try{
            // 파일 읽기
            BufferedReader reader = new BufferedReader(new FileReader(FilePath));
            String line;
            boolean startParsing = false;

            String bassin_cd = null;

            // PostgreSQL DB 연결
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(DBURL, DBUSER, DBPWD);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO INPUT_TANK_DATA(bassin_cd, ms_date, rainfall, evaporation) VALUES (?, ?, ?, ?)");
            int n = 0;
            while ((line = reader.readLine()) != null) {
                if(n == 0) {
                    bassin_cd = line.trim();
                    n = -1;
                }

                // 데이터 파싱 시작 위치 찾기
                if (line.trim().split("\\s+")[0].equals("2007-01-01")) startParsing = true;

                if(startParsing){

//                    System.out.println(line);

                    // 데이터 파싱
                    String[] data = line.split("\\s+");
                    String date = data[0];
                    double value1 = Double.parseDouble(data[1]);
                    double value2 = Double.parseDouble(data[2]);
//                    System.out.println(date + " " + value1 + " " + value2);


                    // 데이터 DB에 저장
                    pstmt.setString(1, bassin_cd);
                    pstmt.setString(2, date);
                    pstmt.setDouble(3, value1);
                    pstmt.setDouble(4, value2);

//                    // 코드 실행
                    pstmt.executeUpdate();
                }

            }

            // 자원 해제
            pstmt.close();
            conn.close();
            reader.close();

            System.out.println("데이터가 성공적으로 DB에 저장되었습니다.");

        }catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
