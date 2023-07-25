package com.example.laos.common.service;

import com.example.laos.common.dao.CommonDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

    private final CommonDao commonDao;

    public Map<String, ArrayList<Map<String,String>>> getTankInputData(String code) {
        ArrayList<Map<String,String>> bassinData = commonDao.selectBassinData(code);
        ArrayList<Map<String, String>> inputData = commonDao.selectTankInputData(code);

//        TankInputData data = TankInputData.createTankInputData(bassinData, inputData);
//        System.out.println(data);

        Map<String, ArrayList<Map<String,String>>> data = new HashMap<>();
        data.put("basicData", bassinData);
        data.put("inputData", inputData);

//        DataToFile(data);
        System.out.println(data);
//        return (HashMap) data;
        return data;

    }

//    private void DataToFile(Map<String, Object> data) {
//        String fileName = "input.txt";
//
//        try (FileWriter writer = new FileWriter(fileName)) {
//            // Write basicData to the file
//            Map<String, String> basicData = (Map<String, String>) data.get("basicData");
//            for (Map.Entry<String, String> entry : basicData.entrySet()) {
//                writer.write(entry.getKey() + "=" + entry.getValue() + System.lineSeparator());
//            }
//            writer.write(System.lineSeparator());
//
//            // Write inputData to the file
//            List<Map<String, String>> inputData = (List<Map<String, String>>) data.get("inputData");
//            for (Map<String, String> entry : inputData) {
//                for (Map.Entry<String, String> inputEntry : entry.entrySet()) {
//                    writer.write(inputEntry.getKey() + "=" + inputEntry.getValue() + " ");
//                }
//                writer.write(System.lineSeparator());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Data has been written to " + fileName);
//
//    }


}
