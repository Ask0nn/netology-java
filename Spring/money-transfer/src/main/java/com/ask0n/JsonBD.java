package com.ask0n;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonBD<T> {
    private final Logger LOGGER = LoggerFactory.getLogger(JsonBD.class);
    private final Path fileName;
    private final Gson gson = new Gson();
    private List<T> list;

    public JsonBD(Path fileName, Class<T[]> tClass, boolean isResource) {
        this.fileName = fileName;
        try {
            if (isResource) writeFromResource();
            if (!Files.exists(fileName)) Files.createFile(fileName);
            if (Files.readString(fileName).equals("")) {
                final FileWriter writer = new FileWriter(fileName.toString(), false);
                writer.write("[]");
                writer.flush();
                writer.close();
            }
            list = new ArrayList<>(Arrays.asList(gson.fromJson(Files.readString(fileName), tClass)));
            saveFile();
        } catch (IOException e) {
            LOGGER.warn(e.getLocalizedMessage());
        }
    }

    public List<T> getList() {
        if (list == null) list = new ArrayList<>();
        return list;
    }

    public void saveFile() {
        try {
            final FileWriter writer = new FileWriter(fileName.toString(), false);
            writer.write(gson.toJson(list));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private void writeFromResource() throws IOException {
        final Resource resourceFile = new ClassPathResource(fileName.toString());

        final InputStream initialStream = resourceFile.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);
        Files.write(fileName, buffer, StandardOpenOption.CREATE);
    }
}
