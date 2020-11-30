package com.test;


import java.io.*;
import java.nio.file.Files;

public class DataWriter {
    private final FileWriter fileWriter;

    public DataWriter() throws IOException  {
        File file = new File("output.txt");
        Files.deleteIfExists(file.toPath());
        boolean result=false;
        result = file.createNewFile();

        fileWriter = new FileWriter(file);

    }

    public void write(String str) throws IOException {
       fileWriter.write(str+"\n");
    }

    public void closeWriter() throws IOException {
        fileWriter.close();
    }
}
