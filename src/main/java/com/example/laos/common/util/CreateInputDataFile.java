package com.example.laos.common.util;

import com.example.laos.vo.TankBasicInputData;
import com.example.laos.vo.TankInputData;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
public class CreateInputDataFile {
    public static void dataToFile(TankBasicInputData basicData, ArrayList<TankInputData> inputData) {
        String fileName = "input.file";

        // 기본데이터
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write basicData to the file

            for (Map<String, String> entry : basicData) {
                for (Map.Entry<String, String> dataEntry : entry.entrySet()) {
                    writer.write(entry.get(dataEntry.getKey()) + System.lineSeparator());
                }
                writer.write(System.lineSeparator());
            }

            
            // 일자별 데이터
            // Write inputData to the file
//            for (Map<String, String> entry : inputData) {
//                for (Map.Entry<String, String> dataEntry : entry.entrySet()) {
//                    writer.write(dataEntry.getValue());
//                }
//                writer.write(System.lineSeparator());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Data has been written to " + fileName);
    }
}
