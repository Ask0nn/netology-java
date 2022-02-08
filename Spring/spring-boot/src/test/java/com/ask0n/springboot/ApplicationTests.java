package com.ask0n.springboot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    public static GenericContainer<?> devApp = new GenericContainer<>("devcondition")
            .withExposedPorts(8080);

    @Container
    public static GenericContainer<?> prodApp = new GenericContainer<>("prodcondition")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp()  {
        devApp.start();
        prodApp.start();
    }

    @Test
    void isDevTest() {
        final String expected = "Current profile is dev";

        ResponseEntity<String> response = restTemplate
                .getForEntity("http://localhost:" + devApp.getMappedPort(8080) + "/profile", String.class);

        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void isProdTest() {
        final String expected = "Current profile is production";

        ResponseEntity<String> response = restTemplate
                .getForEntity("http://localhost:" + prodApp.getMappedPort(8081) + "/profile", String.class);

        Assertions.assertEquals(expected, response.getBody());
    }

}
