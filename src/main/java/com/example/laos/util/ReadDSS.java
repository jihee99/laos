package com.example.laos.util;

import hec.heclib.dss.HecDataManager;
import hec.heclib.dss.HecDssCatalog;
import hec.io.TimeSeriesContainer;

public class ReadDSS {

    // filePath 수정하기
//    private static String filePath = "/C:/Program Files/HEC/HEC-ResSim/3.3/Examples/ExampleWatersheds/HayesBasin_V3.3/rss/Oct_1999_Event/simulation.dss";
    private static String filePath = "D:/SON/2023/__DATA/simulation.dss";

    public static void main(String[] args){


        HecDataManager.setDefaultDSSFileName(filePath);

        String pathname = "//*-controlled outlet/FLOW/*/*/STANDNORM20/";

        //String pathname = "//CP*/FLOW*/*/*/STANDNORM20/";
//        String pathname2 = "//R*/FLOW*/*/*/STANDNORM20/";
        //String pathname = "//RESERVOIR B-UNCONTROLLED SPILLWAY/FLOW-SETTING/01OCT1999/1HOUR/*/";
        //String pathname = "/NPOT/SAVAG/*/01OCT1999/1HOUR/OBS/";

        HecDssCatalog catalog = new HecDssCatalog();
        String paths[] = catalog.getCatalog(true, pathname);


        for (int i=0; i<paths.length; i++) {
            System.out.println(paths[i]);

//
			TimeSeriesContainer tscRead = new TimeSeriesContainer();
//			tscRead.setName(paths[i]);
//
//			HecTimeSeries dssTimeSeriesRead = new HecTimeSeries();
//			//dssTimeSeriesRead.setDSSFileName("C:/temp/Example7.dss");
//			int status = dssTimeSeriesRead.read(tscRead, true);
//			dssTimeSeriesRead.done();
//
//			if (status != 0) return;
//
//			HecTimeArray hTimes = tscRead.getTimes();
//
//			double vals[] = tscRead.getValues();
//
//
//			System.out.println("Average :" + Arrays.stream(vals).average());
//
//
//			for (int j=0; j<tscRead.numberValues; j++) {
//				System.out.println("Ordinate: " + j + ", time: " + hTimes.element(j).dateAndTime() + ", value: " + vals[j]);
//			}

        }



        HecDataManager.closeAllFiles();


    }
}
