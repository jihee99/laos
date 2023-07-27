//package com.example.laos.util;
//
//import com.example.laos.dao.CommonDao;
//import com.example.laos.vo.TankResultData;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Slf4j
//public class ReadTankResultFile {
//// TODO
//// read tank result
//// 컴파일 없이 코드 실행하는 방법은 하단과 같음
//
////String tank = "D:\\dev_etc\\fortran\\OSMTankSim.exe";
////
//
////ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", tank);
////builder.directory(new File("D:\\dev_etc\\fortran"));
////builder.redirectError(ProcessBuilder.Redirect.INHERIT);
//////builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
////
////Process proc = builder.start();
////
////PrintWriter writer=new PrintWriter(proc.getOutputStream());
////writer.write("dc0790\n");
////writer.flush();
////writer.close();
////
////int procResult = proc.waitFor();
////
////if(procResult == 0) { //성공
////    System.out.println("S");
////} else {
////    System.out.println("F");
////}
//
//    private static CommonDao dao;
//    private static final String FILE_ROOT_DIR2 = "D:\\SON\\2023\\00.DATA\\tank";
//
//    public static void main(String[] args) throws IOException {
//        ArrayList<TankResultData> simulationData  = readSimulationReport(FILE_ROOT_DIR2);
//
//    }
//
//    public static ArrayList<TankResultData> readSimulationReport(String filePath) throws IOException {
////        Map<String, Map<String, Double>> simulationData = new HashMap<>();
////        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//        try {
//            Path path = Paths.get(FILE_ROOT_DIR2, "모의", "dc0790.out");
//            List<String> lines = Files.readAllLines(path);
//
//            int idx = 25;
//            int cnt = 0;
//
//            ArrayList<TankResultData> arr = new ArrayList<>();
//            String uuid = UUID.randomUUID().toString();
//
////            while ((line = br.readLine()) != null) {
//            while (idx < lines.size()) {
//                String[] ls = lines.get(idx).replaceAll("\\s\\s+", "Q").split("Q");
//
//                TankResultData vo = new TankResultData(ls);
//                vo.setResultId(UUID.fromString(uuid));
//
//                arr.add(vo);
//
//                idx++;
//                cnt++;
//
//                if (cnt % 1000 == 0) {
//                    dao.insertTnTankBasinSimulationResults(sim);
//                    arr.clear();
//                }
//
//                if (idx < lines.size() && lines.get(idx).contains("----")) {
//                    idx += 2;
//                    break;
//                }
//            }
////            dao.insertTnTankBasinSimulationResults(sim);
//
//            TankResultData trd = new TankResultData();
//
//            trd.setResultId(UUID.fromString(uuid));
//            trd.setSumRainfall(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx).trim().split("=")[1]))));
//            trd.setSumObsFlowDpth(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 1).trim().split("=")[1]))));
//            trd.setSumCompFlowDpth(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 2).trim().split("=")[1]))));
//            trd.setSumEvaport(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 3).trim().split("=")[1]))));
//            trd.setRunoffRatio(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 4).trim().split("=")[1]))));
//
//            idx += 31;
//            trd.setObsMean(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx).trim().split("=")[1]))));
//            trd.setObsSdev(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 1).trim().split("=")[1]))));
//            trd.setSimMean(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 2).trim().split("=")[1]))));
//            trd.setSimSdev(String.valueOf(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 3).trim().split("=")[1]))));
//
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//}
