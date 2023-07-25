package com.example.laos.common.dao;

import com.example.laos.vo.TankBasicInputData;
import com.example.laos.vo.TankInputData;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CommonDao {

//    Map<String, String> selectBasinData(String code);
    TankBasicInputData selectBasinData(String code);
    ArrayList<TankInputData> selectTankInputData(String code);
}
