package com.example.laos.service.impl;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.example.laos.service.DmhFileParsingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DmhFileParsingServiceImpl implements DmhFileParsingService {

	private String DMH_DIR = "D:\\SON\\2023\\00.DATA\\dmh\\";

	private String DMH_FOLDERS = "Water Level";

	private static int BULK_LOAD_DATA = 250;
	
	@Override
	public void dmhEvaporationFileParsing(int pathIdx) {
		String[] folders = DMH_FOLDERS.split(",");

		for(String folder : folders) {
			File[] files = Paths.get(DMH_DIR, folder).toFile().listFiles();
			for(File file : files) {
				if(folder.startsWith("Eva")) {
					log.info(":::: {}" , file.getPath());
					// dmhEvaporationFileParsing(file);
				} else if (folder.startsWith("Rain"))  {
					// dmhRainfallFileParsing(file);
					log.info(":::: {}" , file.getPath());
				} else { // Water Lever
					dmhWaterLevelFileParsing(file);
				}
			}
		}

	}

	// public void dmhEvaporationFileParsing(File file) {
	// 	try(FileInputStream is = new FileInputStream(file)) {
	// 		Workbook workbook = null;
	// 		if (file.getPath().endsWith(".xls")) {
	// 			workbook = new HSSFWorkbook(is);
	// 		} else if (file.getPath().endsWith(".xlsx")) {
	// 			workbook = new XSSFWorkbook(is);
	// 		}
	// 		// 해당 인덱스에 있는 시트를 반환.
	// 		int sheets = workbook.getNumberOfSheets();
	//
	// 		Map<String, TnDmhEvaporationVO> map = new LinkedHashMap<>();
	// 		for(int s = 0; s < sheets; s++) {
	// 			Sheet sheet = workbook.getSheetAt(s);
	// 			String sheetNm = sheet.getSheetName();
	// 			// 모든 행의 개수를 반환.
	// 			int rows = sheet.getLastRowNum();
	//
	// 			for(int i = 0; i <= rows; i++){
	// 				Row row = sheet.getRow(i);
	// 				String station = "";
	// 				String yearStr = "";
	// 				int year = 0;
	// 				if(row != null && row.getCell(0) != null) {
	// 					if(row.getCell(0).getCellType() == CellType.STRING) {
	// 						if(row.getCell(0).getStringCellValue().startsWith("Station")){
	// 							station = row.getCell(0).getStringCellValue().split(":")[1].trim();
	//
	// 							Row yearRow = sheet.getRow(i+2);
	// 							if(yearRow.getCell(6) != null && yearRow.getCell(6).getCellType() == CellType.STRING) {
	// 								if(yearRow.getCell(6).getStringCellValue().toLowerCase(Locale.ROOT).startsWith("year")){
	// 									if(yearRow.getCell(6).getStringCellValue().contains(":")) {
	// 										yearStr = yearRow.getCell(6).getStringCellValue().split(":")[1].trim();
	// 									}else {
	// 										yearStr = yearRow.getCell(6).getStringCellValue().split("\\s")[1].trim();
	// 									}
	//
	// 									if("".equals(yearStr)) {
	// 										year = (int) yearRow.getCell(7).getNumericCellValue();
	// 									} else {
	// 										year = Integer.valueOf(yearStr);
	// 									}
	// 								}
	// 							}
	// 							Row dateRow = sheet.getRow(i+4);
	// 							if(dateRow.getCell(0).getCellType() == CellType.STRING &&
	// 								dateRow.getCell(0).getStringCellValue().startsWith("Date")){
	// 								for(int j = 1; j < 13; j++ ) {
	// 									for(int k = 1; k < 32; k++ ) {
	// 										String mapKey = String.format("%s %s/%s/%s", station, yearStr, j, k);
	// 										TnDmhEvaporationVO vo = new TnDmhEvaporationVO();
	// 										vo.setStation(station);
	// 										vo.setYear(year);
	// 										vo.setMonth(j);
	// 										vo.setDay(k);
	// 										map.putIfAbsent(mapKey, vo);
	// 										Cell dataCell = sheet.getRow(i + k + 4).getCell(j);
	// 										if (dataCell.getCellType() == CellType.NUMERIC) {
	// 											TnDmhEvaporationVO vo1 = map.get(mapKey);
	// 											Double value = dataCell.getNumericCellValue();
	// 											if("Tn".equals(sheetNm)){
	// 												vo1.setMeanMinTemp(value);
	// 											}if("Tx".equals(sheetNm)){
	// 												vo1.setMeanMaxTemp(value);
	// 											}if("Piche".equals(sheetNm)){
	// 												vo1.setObsVal(value);
	// 											}
	// 											map.put(mapKey, vo1);
	//
	//
	//
	// 										} else {
	// 											continue;
	// 										}
	// 									}
	// 								}
	// 							}
	// 						}
	// 					}
	//
	//
	//
	// 				} // if(row != null && row.getCell(0) != null) END
	// 			} // ROWS END
	// 		} // sheets END
	//
	// 		workbook.close();
	//
	// 		for (String key : map.keySet()) {
	// 			TnDmhEvaporationVO vo2 = map.get(key);
	// 			if(vo2.getMeanMaxTemp() != null && vo2.getMeanMinTemp() != null  && vo2.getObsVal() != null ) {
	// 				//                    dao.insertTnEvaporation(vo2);
	// 			}
	// 		}
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// }
	//
	// private void dmhRainfallFileParsing(File file) {
	// 	String[] folders = DMH_FOLDERS.split(",");
	// 	List<String> paths = getFilePathsList();
	// 	List<DmhParsingVO> voList = new ArrayList<>();
	//
	// 	try{
	// 		File target = Paths.get(paths.get(pathIdx)).toFile();
	//
	// 		String dataType = target.getPath().replace(DMH_DIR, "").replace(target.getName(), "").replaceAll("\\\\", "");
	//
	// 		FileInputStream is = new FileInputStream(target);
	// 		Workbook workbook = null;
	// 		if (target.getPath().endsWith(".xls")) {
	// 			workbook = new HSSFWorkbook(is);
	// 		} else if (target.getPath().endsWith(".xlsx")) {
	// 			workbook = new XSSFWorkbook(is);
	// 		}
	// 		// 해당 인덱스에 있는 시트를 반환.
	// 		int sheets = workbook.getNumberOfSheets();
	// 		if(pathIdx != 16) {
	// 			dmhFileParsing1(workbook, sheets, pathIdx, target, dataType);
	// 		} else {
	// 			dmhFileParsing2(workbook, sheets, pathIdx, target, dataType);
	// 		}
	//
	// 	}catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// }

	private void dmhWaterLevelFileParsing(File file){

	}
}
