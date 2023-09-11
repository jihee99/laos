package com.example.laos.service.impl;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.laos.dao.FardDAO;
import com.example.laos.service.FardService;
import com.example.laos.vo.FardInputRefinedData;
import com.example.laos.vo.RainfallData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FardServiceImpl implements FardService {

	@Value("${model.fard.dir}")
	private String dirPath;

	@Value("${model.fard.result}")
	private String resultPath;

	@Value("${model.fard.model}")
	private String modelPath;

	private final FardDAO fardDAO;

	@Override
	public ArrayList<Map<String, Object>> getFardInputDataList() {
		log.info("{}",fardDAO.selectFardInputDataList());
		return fardDAO.selectFardInputDataList();
	}

	@Override
	public String refiningFardInputData(ArrayList<Map<String, Object>> origin) {

		FardInputRefinedData fird = new FardInputRefinedData();

		Map<String, Object> result = new HashMap<>();
		ArrayList<String> years = new ArrayList<>();
		Map<String, List<Double>> columns = new HashMap<>();

		ArrayList<String> yr = new ArrayList<>();

		for (Map<String, Object> entry : origin) {
			String year = (String) entry.get("yr");
			yr.add((String) entry.get("yr"));


			for (Map.Entry<String, Object> dataEntry : entry.entrySet()) {
				String key = dataEntry.getKey();
				//                log.info("{}", dataEntry.ge~tValue());

				if (!key.equals("yr")) {
					Double value = Double.valueOf(String.valueOf(dataEntry.getValue()));
					columns.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
				}else{
					years.add(String.valueOf(dataEntry.getValue()));
				}
			}
			//            result.put("yr", years);

			for (Map.Entry<String, List<Double>> columnEntry : columns.entrySet()) {
				String columnName = columnEntry.getKey();
				List<Double> values = columnEntry.getValue();
				result.put(columnName, values);
			}

			result.put("yr",years);
		}

		log.info("{}", result);
		return generateFardInputFile(result);

	}


	@Override
	public String generateFardInputFile(Map<String, Object> data) {
		String fileName = "INPFILE.DAT";
		String newFilePath = dirPath + fileName;
		try(FileWriter writer = new FileWriter(newFilePath)){
			writer.write("Test\n");							//지점명 (가변)

			List<String> yr = (List<String>) data.get("yr");	//대상자료 시작년도도 (가변)
			if (!yr.isEmpty()) {
				// 첫 번째 년도 가져오기
				String firstYear = yr.get(0);
				writer.write(firstYear + "\n");
			} else {
				// yr 데이터가 비어있는 경우 처리
				writer.write("No data available\n");
			}
			writer.write(String.valueOf(yr.size()) + "\n");			// 자료수 (가변)
			writer.write("6\n");                                    // 강우지속일수(고정 6)
			writer.write("0\n");                                    // 0 		? 필요한 재현기간에 대한 강우량 구하는 경우 1, 옵션선택 0 -> 6,7line 필요 없음
			writer.write("0.05\n");                                 // 0.5 	 	? 빈도개수

			// 강우량 데이터 출력
			writeListToWriter((Object)data.get("d01"), writer, "0001");     //강우지속일1 + 깅우량
			writeListToWriter((Object)data.get("d02"), writer, "0002");     //강우지속일2 + 깅우량
			writeListToWriter((Object)data.get("d03"), writer, "0003");     //강우지속일3 + 깅우량
			writeListToWriter((Object)data.get("d06"), writer, "0006");     //강우지속일6 + 깅우량
			writeListToWriter((Object)data.get("d12"), writer, "0012");     //강우지속일12 + 깅우량
			writeListToWriter((Object)data.get("d18"), writer, "0018");     //강우지속일18 + 깅우량

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		System.out.println("Data has been written to " + fileName);
		executeFardModel(fileName);

		return fileName;

	}

	@Override
	public void executeFardModel(String fileName) {
		//
		// Path sourcePath = Paths.get(fileName);
		// Path targetPath = Paths.get(dirPath + fileName);

		try {
			// Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
			// 모델 실행
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", modelPath);

			builder.directory(new File(dirPath));
			builder.redirectError(ProcessBuilder.Redirect.INHERIT);
			// builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

			Process proc = builder.start();

			PrintWriter writer = new PrintWriter(proc.getOutputStream());
			// writer.write(fileName + "\n");

			writer.flush();
			writer.close();

			String resultFileName = "(OUT)QUANTILE.DAT";
			String resultFilePath = resultPath + resultFileName;

			boolean resultFileExists = checkFileExists(resultFilePath);
			//            boolean processCompleted = proc.waitFor(1, TimeUnit.MINUTES);

			if (resultFileExists) {
				readFardResultData(dirPath, "(OUT)QUANTILE");

				System.out.println("Model execution successful.");
			} else {
				// 지정된 타임아웃 내  프로세스가 완료되지 않았을 경우
				System.out.println("Model execution failed.");
				proc.destroy();
				// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (IOException e) {
			e.printStackTrace();
			// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public void readFardResultData(String filePath, String fileName) {
		Path path = Paths.get(filePath, "Result", fileName+".DAT");

		List<String> lines;
		try {
			lines = Files.readAllLines(path);

			int idx = 543;
			int end = 558;

			while(idx < lines.size()){
				String line = lines.get(idx);
				//            String[] ls = lines.get(idx).replaceAll("\\s\\s+", "Q").split("Q");
				log.info("{}", line);
				idx++;

				if (idx == end) break;
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static void writeListToWriter(Object list, FileWriter writer, String prefix) throws IOException {
		ArrayList<Double> dataList = (ArrayList<Double>)list;
		writer.write(prefix + "\n");
		for (Double value : dataList) {
			if(String.valueOf(value).equals("567.0")) writer.write(String.format("%8.1f", value+0.1));
			else writer.write(String.format("%8.1f", value));
		}
		writer.write("\n");
	}

	private boolean checkFileExists(String filePath) {
		File file = new File(filePath);
		return file.exists() && !file.isDirectory();
	}

}
