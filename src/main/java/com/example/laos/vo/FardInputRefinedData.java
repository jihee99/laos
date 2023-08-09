package com.example.laos.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter @Setter @ToString
public class FardInputRefinedData {

    private ArrayList<String> yr;
    private int dataCnt;
    private int duration;
    @JsonBackReference
    private ArrayList<TimeRainfallData> timeSi;


    private ArrayList<Double> d01;
    private ArrayList<Double> d02;
    private ArrayList<Double> d03;
    private ArrayList<Double> d06;
    private ArrayList<Double> d12;
    private ArrayList<Double> d18;

}

