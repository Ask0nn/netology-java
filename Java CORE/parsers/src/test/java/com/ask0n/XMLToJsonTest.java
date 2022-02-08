package com.ask0n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class XMLToJsonTest {

    @Test
    public void testParse() throws URISyntaxException, JAXBException {
        //given:
        XMLToJson<Staff> xmlToJson = new XMLToJson<>("testData.xml", Staff.class);
        List<Employee> expected = new ArrayList<>();

        expected.add(new Employee(1, "John", "Smith", "USA", 25));
        expected.add(new Employee(2, "Inav", "Petrov", "RU", 23));

        //when:
        Staff parsed = xmlToJson.parseXML();

        //then:
        Assertions.assertEquals(expected, parsed.staff);
    }
}
