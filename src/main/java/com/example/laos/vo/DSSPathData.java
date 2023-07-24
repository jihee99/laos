package com.example.laos.vo;

import hec.io.TimeSeriesContainer;
import lombok.Data;

@Data
public class DSSPathData {
    String pathName;

    String aPart;
    String bPart;
    String cPart;
    String dPart;
    String ePart;
    String fPart;


    TimeSeriesContainer tsc;

    public DSSPathData(String pathName) {
        this.pathName = pathName;
        this.setParts(this.pathName);
    }
    public DSSPathData(String pathName, TimeSeriesContainer tsc) {
        this.pathName = pathName;
        this.tsc = tsc;
        this.setParts(this.pathName);
    }

    public void setParts() {
        String parts[] = this.pathName.split("/");
        this.aPart = parts[1];
        this.bPart = parts[2];
        this.cPart = parts[3];
        this.dPart = parts[4];
        this.ePart = parts[5];
        this.fPart = parts[6];
    }
    public void setParts(String pathName) {
        String parts[] = pathName.split("/");
        this.aPart = parts[1];
        this.bPart = parts[2];
        this.cPart = parts[3];
        this.dPart = parts[4];
        this.ePart = parts[5];
        this.fPart = parts[6];
    }
    public String getPathName() {
        return String.format("/%s/%s/%s/%s/%s/%s", aPart, bPart, cPart, dPart, ePart, fPart);
    }


}