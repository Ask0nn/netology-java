package com.ask0n;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ReadJsonTest {

    @Test
    public void testParseJSON() throws IOException, URISyntaxException {
        //given:
        ReadJson<Staff> readJson = new ReadJson<>("data.json", Staff.class);
        List<Employee> expected = new ArrayList<>();

        expected.add(new Employee(1, "John", "Smith", "USA", 25));
        expected.add(new Employee(2, "Inav", "Petrov", "RU", 23));

        //when:
        String json = readJson.readString();
        List<Employee> parsed = readJson.parseString(json).staff;

        //then:
        assertThat(expected, equalTo(parsed));
    }
}
