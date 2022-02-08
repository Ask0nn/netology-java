package com.ask0n;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVToJson<Employee> csvToJson = new CSVToJson<>("data.csv", Employee.class);
        XMLToJson<Staff> xmlToJson = new XMLToJson<>("data.xml", Staff.class);
        ReadJson<Staff> readJson = new ReadJson<>("data.json", Staff.class);
        try {
            /* CSV */
            List<Employee> csvList = csvToJson.parseCSV();
            csvToJson.writeToJson(csvList, "data.json");
            /* XML */
            Staff xmlList = xmlToJson.parseXML();
            xmlToJson.writeToJson(xmlList, "data2.json");
            /* Read JSON */
            String json = readJson.readString();
            Staff jsonList = readJson.parseString(json);
            jsonList.staff.forEach(x -> System.out.println(x.toString()));
        } catch (IOException | URISyntaxException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
