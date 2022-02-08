package com.ask0n;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class XMLToJson<T> {
    private final String fileName;
    private final Class<T> tClass;

    public XMLToJson(String fileName, Class<T> tClass) {
        this.fileName = fileName;
        this.tClass = tClass;
    }

    public T parseXML() throws JAXBException, URISyntaxException {
        T result;

        File file = new File(ClassLoader.getSystemResource(fileName).toURI());
        JAXBContext jaxbContext = JAXBContext.newInstance(tClass);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        result = (T) unmarshaller.unmarshal(file);

        return result;
    }

    public void writeToJson(Object objToJson, String fileName) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(objToJson);
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.close();
    }
}
