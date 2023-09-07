package com.example.laos.vo;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TnDmhWaterlevelVO {
    String station;
    Integer year;
    Integer month;
    Integer day;
    Double waterlevel;
    Double discharge;
}
