package com.example.laos.common.service;

import com.example.laos.common.dao.CommonDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

    private final CommonDao commonDao;

    @Override
    public List<Map<String, String>> getTankInputData(String code) {

        Map<String, String> bassinData = commonDao.selectBassinData(code);
        log.info("$$");
        System.out.println("$");
        System.out.println(bassinData);
        log.info("{}", bassinData);

//        Map<String, String> tankBasicInputData = commonDao.selectTankBasicInputData(code);
//        List<Map<String, String>> tankInputData = commonDao.selectTankInputData(code);

        return null;
    }

    public List<Map<String,String>> createTankInputData(Map bassin, Map basic, List<Map> input){

        return null;
    }

}
