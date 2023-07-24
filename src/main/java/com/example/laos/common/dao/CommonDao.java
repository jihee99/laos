package com.example.laos.common.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonDao {

    Map<String, String> selectBassinData2(String code);
    Map<String, String> selectTankBasicInputData(String code);
    List<Map<String, String>> selectTankInputData(String code);
}
