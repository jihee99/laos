package com.example.laos.util;

import com.example.laos.vo.TankBasicInputData;
import com.example.laos.vo.TankInputData;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

@Slf4j
public class CreateInputDataFile {
    public static void dataToFile(String code, TankBasicInputData td, ArrayList<TankInputData> tid) {

        String fileName = code;
        // 기본데이터
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write td to the file
            writer.write(td.getBasinCd() + System.lineSeparator());
            writer.write(td.getBasinNm() + System.lineSeparator());
            writer.write(String.format("%10s%10s%10s%n", td.getObsStart(), " ", td.getObsEnd()));
//            writer.write(td.getObsStart()+"          " + td.getObsEnd() + System.lineSeparator());
            writer.write(td.getVoa() + td.getVom() + System.lineSeparator());
            writer.write(td.getBasinArea() + System.lineSeparator());

            Field[] fields1 = TankBasicInputData.class.getDeclaredFields();
            for (Field field : fields1) {
                field.setAccessible(true);

                String fieldName = field.getName();
                String fieldValue = String.valueOf(field.get(td));
//                if(fieldValue.equals("0")) fieldValue = "0.0";
                if (fieldName.equals("xsi") || fieldName.equals("xai")
                    || fieldName.equals("xbi") || fieldName.equals("xci") || fieldName.equals("xdi")
                    || fieldName.equals("s1") || fieldName.equals("s2") || fieldName.equals("k1")
                    || fieldName.equals("k2") || fieldName.equals("a2") || fieldName.equals("a1")
                    || fieldName.equals("a0") || fieldName.equals("b1") || fieldName.equals("b0")
                    || fieldName.equals("c1") || fieldName.equals("c0") || fieldName.equals("d1")
                    || fieldName.equals("ha2") || fieldName.equals("ha1") || fieldName.equals("hb")
                    || fieldName.equals("hc") || fieldName.equals("u1") || fieldName.equals("u2")) {
                    writer.write(String.format("%10s%10s%7s%s%n", " ", fieldValue, " ", fieldName.toUpperCase()));
                }
            }

            // 일자별 데이터
            // Write inputData to the file
            for (TankInputData data : tid) {

//                Field[] fields2 = TankInputData.class.getDeclaredFields();
                for(Field field : TankInputData.class.getDeclaredFields()){
                    field.setAccessible(true);

                    String fieldName = field.getName();
                    String fieldValue = String.valueOf(field.get(data));
                    if(fieldValue.equals("null")) fieldValue = " ";

                    if(fieldName.equals("obsDate")) writer.write(String.format("%s", fieldValue));
                    if (fieldName.equals("rainfall") || fieldName.equals("value1") || fieldName.equals("evaporation")){
                        writer.write(String.format("%10s", fieldValue));
                    }
                    if(fieldName.equals("evaporation")) writer.write(System.lineSeparator());
                }
            }

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println("Data has been written to " + fileName);
    }
}
