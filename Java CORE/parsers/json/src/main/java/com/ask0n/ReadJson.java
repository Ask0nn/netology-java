package com.ask0n;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadJson<T> {
    private final String fileName;
    private final Class<T> tClass;

    public ReadJson(String fileName, Class<T> tClass) {
        this.fileName = fileName;
        this.tClass = tClass;
    }

    public String readString() throws URISyntaxException, IOException {
        String json = Files.readString(Paths.get(ClassLoader.getSystemResource(fileName).toURI()));
        return json;
    }

    public T parseString(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, tClass);
    }
}
