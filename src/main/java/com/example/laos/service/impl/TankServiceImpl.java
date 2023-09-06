package com.example.laos.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.laos.service.TankService;
import com.example.laos.vo.EstimateInflowVo;
import com.example.laos.vo.EstimateSummaryVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TankServiceImpl implements TankService {

	@Value("${model.tank.ResultPath}")
	private String resultPath;

	@Value("${model.tank.TemplateFilePath}")
	private String templatePath;

	@Value("${model.tank.ModelPath}")
	private String modelPath;

	// 업로드용 엑셀 생성 서비스
	@Override
	public void GenerateAndDownloadTankInputExcel(HttpServletResponse response) {
		String filePath = "D:\\dev_etc\\tank\\newfile";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));

			Workbook workbook = new XSSFWorkbook();
			{

				Sheet sheet = workbook.createSheet("TankData");
				Row headerRow = sheet.createRow(0);

				String[] header = {"일자", "강우", "유출량"};
				for (int i = 0; i < header.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(header[i]);
				}

				String line;
				boolean startParsing = false;
				int rowNumber = 1;
				int lineNum = 0;

				while ((line = reader.readLine()) != null) {
					System.out.println(line);
					lineNum++;
					if (lineNum == 29) {
						startParsing = true;
					}
					if (startParsing) {
						String[] values = line.split("\\s+");
						Row row = sheet.createRow(rowNumber++);
						for (int i = 0; i < values.length; i++) {
							Cell cell = row.createCell(i);
							cell.setCellValue(values[i]);
						}
					}
				}

			}

			String excelFilePath = "tank_data.xlsx";

			try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
				workbook.write(outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			workbook.close();

			// 엑셀 다운로드 코드 추가
			downloadExcelFile(response, excelFilePath);

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void downloadExcelFile(HttpServletResponse response, String excelPath) {
		File file = new File(excelPath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());

		ServletOutputStream out = null;

		try {
			out = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}

			fis.close();
			out.flush();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	// 모델용 입력 파일 생성 서비스
	public EstimateSummaryVo createUploadInputFile(MultipartFile file) {
		// 업로드된 파일 처리 로직 작성
		// 입력 파일 생성용 빌더
		StringBuilder tankContent = new StringBuilder();

		// 파라미터 템플릿 파일 읽기
		String templateFilePath = templatePath + "dc0790_param";
		Path path = Paths.get(templateFilePath);
		String fileName;

		try (BufferedReader reader = new BufferedReader(new FileReader(templateFilePath))) {
			fileName = reader.readLine();
			log.info("{}", "Read filename:" + fileName);

			// 엑셀 파일 읽기
			List<List<String>> excelData = readExcel(file.getInputStream());

			// 시작일과 종료일 구하기
			String startDate = excelData.get(1).get(0);
			String endDate = excelData.get(excelData.size() - 1).get(0);

			List<String> lines = Files.readAllLines(path);
			int idx = 0;

			// 시작일과 종료일을 템플릿 파일에 넣기
			while(idx<lines.size()){
				if (lines.get(idx).contains("$${StartDate}")) {
					lines.set(idx, lines.get(idx).replace("$${StartDate}", formatDate(startDate)));
				}
				if (lines.get(idx).contains("$${EndDate}")) {
					lines.set(idx, lines.get(idx).replace("$${EndDate}", formatDate(endDate)));
				}

				tankContent.append(lines.get(idx)).append("\n");
				idx++;
			}

			for(int i=1; i<excelData.size(); i++){
				tankContent.append(formatData10(String.valueOf(excelData.get(i)))).append("\n");
			}

			// 새 파일 생성 및 내용 작성
			String newFilePath = resultPath + "\\tank\\" + fileName;
			try (FileWriter writer = new FileWriter(newFilePath)) {
				writer.write(tankContent.toString());
				System.out.println("Success create Input file.");

				String targetPath = resultPath + "\\tank\\" + fileName.trim();
				Path source = Paths.get(newFilePath);
				Path target = Paths.get(targetPath);
				Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("파일을 원하는 경로로 이동했습니다.");
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 모델 실행
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", modelPath);


			builder.directory(new File(resultPath + "\\tank\\"));
			builder.redirectError(ProcessBuilder.Redirect.INHERIT);
			builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

			Process proc = builder.start();

			PrintWriter writer = new PrintWriter(proc.getOutputStream());
			writer.write(fileName.trim() + "\n");
			writer.flush();


			int procResult = proc.waitFor();

			if(procResult == 0) { //성공
				System.out.println("Model execution successful.");
			} else {
				System.out.println("Model execution failed.");
			}
			// 모델 결과 파일 읽어오기
			EstimateSummaryVo es = readEstimateInflowModelResult(fileName);
			// 처리 완료 후 리턴
			return es;

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}

	public List<List<String>> readExcel(InputStream inputStream) {
		List<List<String>> excelData = new ArrayList<>();
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(inputStream);

			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				List<String> rowData = new ArrayList<>();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					rowData.add(cell.getStringCellValue()); // 각 셀의 값을 문자열로 읽음
				}
				excelData.add(rowData);
			}

			workbook.close();

			return excelData;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public String formatDate(String date) {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date parsedDate = null;
		try {
			parsedDate = inputFormat.parse(date);
			return outputFormat.format(parsedDate);
		} catch (ParseException e) {
			//            throw new RuntimeException(e);
			return null;
		}
	}

	@Override
	public String formatData10(String input) {
		String[] parts = input.replace("[", "").replace("]", "").split(", ");
		return String.format("%10s%10s%10s%10s", parts[0], parts[1], " ", parts[2]);
	}

	@Override
	public String formatData12(String input) {
		String[] parts = input.replace("[", "").replace("]", "").split(", ");
		return String.format("%12s%12s%12s%12s", parts[0], parts[1], " ", parts[2]);
	}

	@Override
	public EstimateSummaryVo readEstimateInflowModelResult(String fileName) {
		EstimateSummaryVo es = new EstimateSummaryVo();
		try {

			Path path = Paths.get(resultPath, "tank", fileName+".out");
			List<String> lines = Files.readAllLines(path);

			int idx = 25;

			//            ArrayList<TankResultData> arr = new ArrayList<>();
			ArrayList<EstimateInflowVo> eilist = new ArrayList<>();
			String uuid = UUID.randomUUID().toString();
			Boolean startParsing = true;

			while (idx< lines.size()) {
				String[] ls = lines.get(idx).replaceAll("\\s\\s+", "Q").split("Q");
				EstimateInflowVo eif = new EstimateInflowVo();

				eif.setDate(ls[0].replaceAll("\\s+", ""));
				eif.setRMm(ls[1].equals("**********") ? "0.0" : ls[1] );
				eif.setQoCms(ls[2].startsWith("*****") ? "0.0" : ls[2]);
				eif.setQsCms(ls[3].startsWith("*****") ? "0.0" : ls[3]);

				eilist.add(eif);
				idx++;

				if(idx < lines.size() && lines.get(idx).contains("-----")){
					idx += 2;
					break;
				}
			}

			System.out.println(idx);
			es.setRealRainfall(lines.get(idx).trim().split("=")[1].startsWith("*****") ? "0.00" : lines.get(idx).trim().split("=")[1].trim());
			es.setObservedFlowDept(lines.get(idx+1).trim().split("=")[1].startsWith("*****") ? "0.00" : lines.get(idx+1).trim().split("=")[1].trim());
			es.setComputedFlowDept(lines.get(idx+2).trim().split("=")[1].startsWith("*****") ? "0.00" : lines.get(idx+2).trim().split("=")[1].trim());
			es.setEvapotranspiration(lines.get(idx+3).trim().split("=")[1].startsWith("*****") ? "0.00" : lines.get(idx+3).trim().split("=")[1].trim());

			// ratio 값이 Obs. Ratio /  Com. Ratio 두 개로 분리
			// Obs. Ratio = 0.00 -> Com. Ratio 사용
			es.setRatio(lines.get(idx+6).trim().split("=")[1].trim().startsWith("*****") ? "0.00" : lines.get(idx+6).trim().split("=")[1].trim());

			idx += 33;
			es.setObsMean(lines.get(idx).trim().split("=")[1].startsWith("*****") ? "0.000" : lines.get(idx++).trim().split("=")[1].trim());
			es.setObsSdev(lines.get(idx).trim().split("=")[1].startsWith("*****") ? "0.000" : lines.get(idx++).trim().split("=")[1].trim());
			es.setSimMean(lines.get(idx).trim().split("=")[1].startsWith("*****") ? "0.000" : lines.get(idx++).trim().split("=")[1].trim());
			es.setSimSdev(lines.get(idx).trim().split("=")[1].trim().startsWith("*****") ? "0.000" : lines.get(idx).split("=")[1].trim());

			es.setInflows(eilist);

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return es;
	}

}
