package com.example.laos.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TankResultData {

    private String resultId;
    private String obsDate;
    private String r;
    private String qoCms;
    private String qsCms;
    private String qoMm;
    private String qsMm;
    private String xs;
    private String xa;
    private String xb;
    private String xc;
    private String xd;
    private String q1;
    private String q2;
    private String q3;
    private String q4;
    private String et;


    private String sumRainfall;
    private String sumObsFlowDpth;
    private String sumCompFlowDpth;
    private String sumEvaport;
    private String runoffRatio;


    private String obsMean;
    private String obsSdev;
    private String simMean;
    private String simSdev;


    // Constructor
    public TankResultData(String[] lineData) {
        if (lineData.length >= 16) {
            this.obsDate = lineData[0];
            this.r = lineData[1];
            this.qoCms = lineData[2];
            this.qsCms = lineData[3];
            this.qoMm = lineData[4];
            this.qsMm = lineData[5];
            this.xs = lineData[6];
            this.xa = lineData[7];
            this.xb = lineData[8];
            this.xc = lineData[9];
            this.xd = lineData[10];
            this.q1 = lineData[11];
            this.q2 = lineData[12];
            this.q3 = lineData[13];
            this.q4 = lineData[14];
            this.et = lineData[15];
        }
    }

}
