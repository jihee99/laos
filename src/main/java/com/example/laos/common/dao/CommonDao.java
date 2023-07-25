package com.example.laos.common.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface CommonDao {

//    Map<String, String> selectBassinData(String code);
    ArrayList<Map<String,String>>  selectBassinData(String code);
    ArrayList<Map<String, String>> selectTankInputData(String code);
}
