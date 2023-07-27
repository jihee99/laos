package com.example.laos.service;

import com.example.laos.dao.CommonDao;
import com.example.laos.vo.TankBasicInputData;
import com.example.laos.vo.TankInputData;
import com.example.laos.vo.TankResultData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

    private final CommonDao commonDao;

    @Transactional
    public Map<String, Object> getTankInputData(String code) {

        TankBasicInputData basicData = commonDao.selectBasinData(code);
        ArrayList<TankInputData> inputData;

        if(code.equals("cscal")) inputData = commonDao.selectTankInputDataCscal(code);
        else inputData = commonDao.selectTankInputData(code);
//        ArrayList<TankInputData> inputData = commonDao.selectTankInputData(code);

        Map<String, Object> result = new HashMap<>();
        result.put("basicData", basicData);
        result.put("inputData", inputData);

        return result;

    }


    public void insertTankSimulationResults(ArrayList<TankResultData> arr){
        commonDao.insertTankSimulationResult(arr);
    }

    public void insertTnTankBasinResults(TankResultData tn){
        System.out.println("################");
        log.info("{}", tn);
        commonDao.insertTnTankBasinResults(tn);
    }



}
