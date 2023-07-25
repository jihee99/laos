package com.example.laos.common.service;

import java.util.ArrayList;
import java.util.Map;

public interface CommonService {

    Map<String, ArrayList<Map<String,String>>> getTankInputData(String code);

}
