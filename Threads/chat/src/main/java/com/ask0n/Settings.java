package com.ask0n;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Settings {
    private static final String FILE_NAME = "settings.json";
    private static Settings instance;

    private String host;
    private int port;

    private Settings() {}

    public static Settings getInstance() {
        if (instance == null) {
            try {
                URL resource = Thread.currentThread().getContextClassLoader().getResource(FILE_NAME);
                if (resource == null) throw new FileNotFoundException("Settings file not found");
                Gson gson = new Gson();
                String json = Files.readString(Paths.get(resource.toURI()));
                instance = gson.fromJson(json, Settings.class);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace(System.err);
                System.err.println(e.getMessage());
            }
        }
        return instance;
    }

    public String getHost() {
        return host;
    }
    public int getPort() {
        return port;
    }
}
