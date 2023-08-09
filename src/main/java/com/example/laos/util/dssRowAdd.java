package com.example.laos.util;

import hec.heclib.dss.HecDataManager;
import hec.heclib.dss.HecDssCatalog;
import hec.heclib.dss.HecTimeSeries;
import hec.heclib.util.HecTime;
import hec.heclib.util.HecTimeArray;
import hec.io.TimeSeriesContainer;
import javax.swing.UIManager;

public class dssRowAdd {
    private static String fileName = "C:/HY/laos/dss/forJava/make_dss.dss";
    private static String catalogPathname = "/N*/1096/FLOW/*/1DAY/*/";
    private static String dataType = "INST-VAL";
    private static String units = "M3/S";
    private static String addCatalogPath = "/NAMLIK UP NN/1096/FLOW/01JAN2001/1DAY/UNS_01_ORD/";

    // 해당 부분 수정 필요
    private static String myCatalogPath = "/JBT//JHY//1DAY//";

    public dssRowAdd() {
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception var10) {
        }

        int valueCount = 365;
        double[] data = new double[valueCount];
        HecDataManager.setDefaultDSSFileName(fileName);
        HecDssCatalog catalog = new HecDssCatalog();
        catalog.getCatalog(false, addCatalogPath);
        TimeSeriesContainer tscAddRow = new TimeSeriesContainer();
        tscAddRow.setName(myCatalogPath);
        HecTimeSeries dssTimeSeries = new HecTimeSeries();
        HecTime start = new HecTime("01Jan2001", "2400");
        HecTimeArray hectimeArray = new HecTimeArray(valueCount);

        for(int i = 1; i < valueCount + 1; ++i) {
            data[i - 1] = Math.pow((double)i, 2.0D);
            hectimeArray.setElementAt(new HecTime(start), i - 1);
        }

        dssTimeSeries.setStartTime(start);
        tscAddRow.setType(dataType);
        tscAddRow.setUnits(units);
        tscAddRow.setValues(data);
        tscAddRow.set(data, hectimeArray);
        int status = dssTimeSeries.write(tscAddRow);
        System.out.println("write code Value : " + status);
        dssTimeSeries.done();
        HecDataManager.closeAllFiles();
    }
}
