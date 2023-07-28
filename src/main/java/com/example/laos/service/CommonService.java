package com.example.laos.service;

import com.example.laos.vo.TankResultData;

import java.util.ArrayList;
import java.util.Map;

public interface CommonService {

    Map<String, Object> getTankInputData(String code);
    void insertTankSimulationResults(ArrayList<TankResultData> sim);
    void insertTnTankBasinResults(TankResultData arr);

    ArrayList<Map<String, String>> getFardInputDataList();

}
