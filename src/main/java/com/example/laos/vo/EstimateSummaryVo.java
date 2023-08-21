package com.example.laos.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Data
@Getter
@Setter
@NoArgsConstructor
public class EstimateSummaryVo {

    private String realRainfall;
    private String observedFlowDept;
    private String computedFlowDept;
    private String evapotranspiration;
    private String ratio;

    private String obsMean;
    private String obsSdev;
    private String simMean;
    private String simSdev;

    private ArrayList<EstimateInflowVo> inflows;

}
