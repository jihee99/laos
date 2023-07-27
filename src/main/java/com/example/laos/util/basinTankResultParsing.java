//package com.example.laos.util;
//
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//public class basinTankResultParsing {
//
//    public void basinTankResultParsing() {
//        try {
//            Path path = Paths.get(FILE_ROOT_DIR2, "모의", "dc0790찐결과.out");
//            List<String> lines = Files.readAllLines(path);
//
//            int idx = 25;
//            int cnt = 0;
//            List<TnTankBasinSimulationResultsVo> sim = new ArrayList<>();
//            String uuid = UUID.randomUUID().toString();
//
//            while (idx < lines.size()) {
//                String[] ls = lines.get(idx).replaceAll("\\s\\s+", "Q").split("Q");
//
//                TnTankBasinSimulationResultsVo vo = new TnTankBasinSimulationResultsVo();
//
//                vo.setResultId(uuid);
//                vo.setObsDate(ls[0].replaceAll("\\s+", ""));
//                vo.setRMm(BigDecimal.valueOf(Double.valueOf(ls[1])));
//                vo.setQoCms(BigDecimal.valueOf(Double.valueOf(ls[2])));
//                vo.setQsCms(BigDecimal.valueOf(Double.valueOf(ls[3])));
//                vo.setQoMm(BigDecimal.valueOf(Double.valueOf(ls[4])));
//                vo.setQsMm(BigDecimal.valueOf(Double.valueOf(ls[5])));
//                vo.setXsMm(BigDecimal.valueOf(Double.valueOf(ls[6])));
//                vo.setXaMm(BigDecimal.valueOf(Double.valueOf(ls[7])));
//                vo.setXbMm(BigDecimal.valueOf(Double.valueOf(ls[8])));
//                vo.setXcMm(BigDecimal.valueOf(Double.valueOf(ls[9])));
//                vo.setXdMm(BigDecimal.valueOf(Double.valueOf(ls[10])));
//                vo.setEtMm(BigDecimal.valueOf(Double.valueOf(ls[11])));
//
//                sim.add(vo);
//
//                idx++;
//                cnt++;
//                if (cnt % 1000 == 0) {s
//                    dao.insertTnTankBasinSimulationResults(sim);
//                    sim.clear();
//                }
//
//
//                if (idx < lines.size() && lines.get(idx).contains("----")) {
//                    idx += 2;
//                    break;
//                }
//            }
//            dao.insertTnTankBasinSimulationResults(sim);
//
//            // sum 시작
//            TnTankBasinResultsVo ttbrv = new TnTankBasinResultsVo();
//            ttbrv.setResultId(uuid);
//            ttbrv.setSumRainfall(BigDecimal.valueOf(Double.valueOf(lines.get(idx).trim().split("=")[1])));
//            ttbrv.setSumObsFlowDpth(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 1).trim().split("=")[1])));
//            ttbrv.setSumCompFlowDpth(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 2).trim().split("=")[1])));
//            ttbrv.setSumEvaport(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 3).trim().split("=")[1])));
//            ttbrv.setRunoffRatio(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 4).trim().split("=")[1])));
//
//            idx += 31;
//            ttbrv.setObsMean(BigDecimal.valueOf(Double.valueOf(lines.get(idx).trim().split("=")[1])));
//            ttbrv.setObsSdev(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 1).trim().split("=")[1])));
//            ttbrv.setSimMean(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 2).trim().split("=")[1])));
//            ttbrv.setSimSdev(BigDecimal.valueOf(Double.valueOf(lines.get(idx + 3).trim().split("=")[1])));
//
//            dao.insertTnTankBasinResults(ttbrv); // insert
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}