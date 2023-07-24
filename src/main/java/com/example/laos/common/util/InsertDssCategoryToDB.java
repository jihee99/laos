package com.example.laos.common.util;

import com.example.laos.vo.DSSPathData;
import hec.heclib.dss.HecDssCatalog;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class InsertDssCategoryToDB {

    public static String DBURL = "jdbc:log4jdbc:postgresql://localhost:5432/laos_dss";
    public static String DBUSER = "postgres";
    public static String DBPASSWORD = "postgres00";
    static String ROOT = "D:/SON/2023/__DATA/forJava";
    static String[] fileNames = {"HEC-HMS_NN_DEPP.dss", "NN_SCN1.dss", "Met_1.dss", "NN_DEPP.dss"};
    static int MAX_BATCH_SIZE = 5000;

    public static void main (String args[]) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int[] rs = null;

        for(String fileName : fileNames) {
            HecDssCatalog catalog = new HecDssCatalog(Paths.get(ROOT, fileName).toString());
            UUID fileId = UUID.randomUUID();

            try {
                List<String> paths = Arrays.asList(catalog.getCatalog(true, "/*/*/*/*/*/*/"));

                String sql = "INSERT INTO public.dss_files" +
                        "(file_id, file_nm, file_desc) " +
                        "VALUES(?::UUID, ?, ?);";

                conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
                conn.setAutoCommit(false);

                /* 파일명 넣기 */
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, fileId.toString());
                pstmt.setString(2, fileName);
                pstmt.setString(3, "라오스 DEPP 현 유역유출 모형 DSS 파일, "+fileName);
                pstmt.execute();
                pstmt.clearParameters();
                conn.commit();
                /* 파일명 넣기 */


                sql = 	"INSERT INTO public.dss_parts" +
                        "(a_part, b_part, c_part, d_part, e_part, f_part, file_id) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?::UUID);";

                pstmt = conn.prepareStatement(sql);

                int batchSize = 0;
                for(String path : paths) {
                    DSSPathData dssPathData = new DSSPathData(path);

                    pstmt.setString(1, dssPathData.getAPart());
                    pstmt.setString(2, dssPathData.getBPart());
                    pstmt.setString(3, dssPathData.getCPart());
                    pstmt.setString(4, dssPathData.getDPart());
                    pstmt.setString(5, dssPathData.getEPart());
                    pstmt.setString(6, dssPathData.getFPart());
                    pstmt.setObject(7, fileId.toString());

                    pstmt.addBatch();
                    pstmt.clearParameters();

                    batchSize++;
                    if(batchSize == MAX_BATCH_SIZE) {
                        conn.commit();
                        batchSize = 0;
                    }

                }
                rs = pstmt.executeBatch();
                pstmt.clearParameters();
                conn.commit();


            } catch (SQLException e) {
                log.error("{}", e);

                conn.rollback();
            } finally {
                catalog.closeDSSFile();
                pstmt.close();
                conn.close();
            }
        }
    }
}
