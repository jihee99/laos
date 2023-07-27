package com.example.laos.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DSSReader {

    private static String filePath = "/D:/SON/2023/__DATA/simulation.dss";

    public static void main(String[] agrs) throws IOException {

        // Open the DSS file
        FileInputStream file = new FileInputStream(new File(filePath));

        // Get the workbook instance for the DSS file
        HSSFWorkbook workbook = new HSSFWorkbook(file);

        // Get the sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        // Iterate through each row of the sheet
        for (int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {
            HSSFRow row = sheet.getRow(rowIndex);
            if (row == null) {
                continue;
            }

            // Iterate through each cell of the row
            for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                HSSFCell cell = row.getCell(cellIndex);
                if (cell == null) {
                    continue;
                }

                // Print the value of the cell
                System.out.print(cell.toString() + "\t");
            }
            System.out.println();
        }

        // Close the workbook and the file
        workbook.close();
        file.close();
    }
}
