package com.example.laos.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class TankInputData {
    private String basinCd;
    private String obsDate;
    private String rainfall;
    private String value1;
    private String evaporation;
}
