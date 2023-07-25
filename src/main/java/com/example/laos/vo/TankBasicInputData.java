package com.example.laos.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class TankBasicInputData {
    private String bassin_cd;
    private String bassin_nm;
    private String xsi;
    private String xai;
    private String xbi;
    private String xci;
    private String xdi;
    private String s1;
    private String s2;
    private String k1;
    private String k2;
    private String a2;
    private String a1;
    private String a0;
    private String b1;
    private String b0;
    private String c1;
    private String c0;
    private String d1;
    private String ha2;
    private String ha1;
    private String hb;
    private String hc;
    private String u1;
    private String u2;

    private List<Map<String, String>> rainfallData;

    public static TankBasicInputData createTankInputData(Map<String, String> basicData, List<Map<String, String>> inputData) {
        TankBasicInputData tankInputData = new TankBasicInputData();

        int width = 10;

        // basicData 설정
        tankInputData.setBassin_cd(basicData.get("bassin_cd"));
        tankInputData.setBassin_nm(basicData.get("bassin_nm"));
        tankInputData.setXsi(basicData.get("xai"));
        tankInputData.setXai(basicData.get("xai"));
        tankInputData.setXbi(basicData.get("xbi"));
        tankInputData.setXci(basicData.get("xci"));
        tankInputData.setXdi(basicData.get("xdi"));
        tankInputData.setS1(basicData.get("s1"));
        tankInputData.setS2(basicData.get("s2"));
        tankInputData.setK1(basicData.get("k1"));
        tankInputData.setK2(basicData.get("k2"));
        tankInputData.setA2(basicData.get("a2"));
        tankInputData.setA1(basicData.get("a1"));
        tankInputData.setA0(basicData.get("a0"));
        tankInputData.setB1(basicData.get("b1"));
        tankInputData.setB0(basicData.get("b0"));
        tankInputData.setC1(basicData.get("c1"));
        tankInputData.setC0(basicData.get("c0"));
        tankInputData.setD1(basicData.get("d1"));
        tankInputData.setHa2(basicData.get("ha2"));
        tankInputData.setHa1(basicData.get("ha1"));
        tankInputData.setHb(basicData.get("hb"));
        tankInputData.setHc(basicData.get("hc"));
        tankInputData.setU1(basicData.get("u1"));
        tankInputData.setU2(basicData.get("u2"));

        // inputData 설정
        tankInputData.setRainfallData(inputData);

        return tankInputData;
    }


}
