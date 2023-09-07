package com.example.laos.vo;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TnDmhRainfallVO {
    String station;
    Integer year;
    Integer month;
    Integer day;
    Double rainfall;
}
