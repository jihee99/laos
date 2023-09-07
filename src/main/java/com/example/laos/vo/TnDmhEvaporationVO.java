package com.example.laos.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TnDmhEvaporationVO {
    String station;
    Integer year;
    Integer month;
    Integer day;
    Double obsVal;
    Double meanMinTemp;
    Double meanMaxTemp;
}
