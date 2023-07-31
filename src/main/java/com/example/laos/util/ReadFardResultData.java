package com.example.laos.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class ReadFardResultData {

    public static void readFardResultData(String filePath, String fileName) throws IOException {

//        Path path = Paths.get(filePath, "result", fileName+".DAT");
        Path path = Paths.get(filePath, "Result", fileName+".DAT");

        List<String> lines = Files.readAllLines(path);

        int idx = 543;
        int end = 558;

        while(idx < lines.size()){
            String line = lines.get(idx);
//            String[] ls = lines.get(idx).replaceAll("\\s\\s+", "Q").split("Q");
            log.info("{}", line);
            idx++;

            if (idx == end) break;
        }
    }
}
