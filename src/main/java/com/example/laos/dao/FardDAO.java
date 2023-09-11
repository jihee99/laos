package com.example.laos.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FardDAO {
	ArrayList<Map<String, Object>> selectFardInputDataList();
}
