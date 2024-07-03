package com.proj.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.proj.demo.service.ClientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DemoApplication.class)
class ClientTest {

    @Autowired
    private ClientService clientService;
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);
        clientService.setBaseUrl("http://localhost:8080");
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void shouldReturnLongValue() {
        WireMock.stubFor(WireMock.get("/long-value?alphanumericParam=123abc")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withStatus(200)
                        .withBody("12345")
                ));
        Long result = clientService.getLongValue("123abc");
        assertEquals(12345L, result);
    }

    @Test
    public void testGetStringList() {
        // Задаем стаб-запрос с ожидаемым ответом
        WireMock.stubFor(WireMock.get("/string-list")
                .withHeader("alphanumericHeader", WireMock.equalTo("xyz456"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("[ \"value1\", \"value2\", \"value3\" ]")
                ));

        // Вызов метода API-клиента
        List<String> result = clientService.getStringList("xyz456");

        // Проверка результата
        assertEquals(Arrays.asList("value1", "value2", "value3"), result);
    }
}
