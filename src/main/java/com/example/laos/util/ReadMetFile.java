package com.example.laos.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadMetFile {

    private static String FilePath = "D:\\dev_etc\\hms\\";
//    private static String FilePath = "D:\\dev_etc\\hms\\";

    public static void main(String[] args) throws IOException {
        String fileName = "Met_1.met";
        FileInputStream file = new FileInputStream(FilePath+fileName);

        Path path = Paths.get(FilePath, "hms", fileName);

        List<String> lines = Files.readAllLines(path);

        int idx = 0;
        while(idx<lines.size()){

        }



    }
}
