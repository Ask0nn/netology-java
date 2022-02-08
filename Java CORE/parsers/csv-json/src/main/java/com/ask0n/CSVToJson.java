package com.ask0n;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSVToJson<T> {
    private final String fileName;
    private final Class<T> tClass;

    public CSVToJson(String fileName, Class<T> tClass) {
        this.fileName = fileName;
        this.tClass = tClass;
    }

    public List<T> parseCSV() throws IOException, URISyntaxException {
        Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource(fileName).toURI()));
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                .withSeparator(',')
                .withType(tClass)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        return csvToBean.parse();
    }

    public void writeToJson(List<T> objToJson, String fileName) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(objToJson);
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.close();
    }
}
