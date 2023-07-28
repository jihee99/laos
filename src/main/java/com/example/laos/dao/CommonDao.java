package com.example.laos.dao;

import com.example.laos.vo.TankBasicInputData;
import com.example.laos.vo.TankInputData;
import com.example.laos.vo.TankResultData;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface CommonDao {

//    Map<String, String> selectBasinData(String code);
    TankBasicInputData selectBasinData(String code);
    ArrayList<TankInputData> selectTankInputData(String code);
    ArrayList<TankInputData> selectTankInputDataCscal(String code);

    void insertTankSimulationResults(ArrayList<TankResultData> arr);

    void insertTnTankBasinResults(TankResultData tn);

    ArrayList<Map<String, String>> selectFardInputDataList();
}
