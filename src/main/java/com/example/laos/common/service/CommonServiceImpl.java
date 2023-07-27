package com.example.laos.common.service;

import com.example.laos.common.dao.CommonDao;
import com.example.laos.vo.TankBasicInputData;
import com.example.laos.vo.TankInputData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

    private final CommonDao commonDao;

    @Transactional
    public Map<String, Object> getTankInputData(String code) {

        TankBasicInputData basicData = commonDao.selectBasinData(code);
        ArrayList<TankInputData> inputData;

        if(code.equals("cscal")) inputData = commonDao.selectTankInputDataCscal(code);
        else inputData = commonDao.selectTankInputData(code);
//        ArrayList<TankInputData> inputData = commonDao.selectTankInputData(code);

        Map<String, Object> result = new HashMap<>();
        result.put("basicData", basicData);
        result.put("inputData", inputData);

        return result;

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
