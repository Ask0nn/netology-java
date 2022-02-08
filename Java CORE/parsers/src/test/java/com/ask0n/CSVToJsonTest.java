package com.ask0n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CSVToJsonTest {

    @Test
    public void testParse() throws IOException, URISyntaxException {
        //given:
        CSVToJson<Employee> csvToJson = new CSVToJson<>("testData.csv", Employee.class);
        List<Employee> expected = new ArrayList<>();

        expected.add(new Employee(1, "John", "Smith", "USA", 25));
        expected.add(new Employee(2, "Inav", "Petrov", "RU", 23));

        //when:
        List<Employee> parsed = csvToJson.parseCSV();

        //then:
        Assertions.assertEquals(expected, parsed);
    }
}
