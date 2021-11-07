package ru.sibintek.test;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = JsonToDbApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class JsonToDbApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl() {
        return "http://localhost:" + port + "/json_message";
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }

    private final String message14 = "{\r\n"
            + "  \"id\": 14,\r\n"
            + "  \"login\": \"john\",\r\n"
            + "  \"password\": \"sdH4k\"\r\n"
            + "}";
    private final String message52 = "{\r\n"
            + "  \"id\": 52,\r\n"
            + "  \"name\": \"user\",\r\n"
            + "  \"description\": \"role user\"\r\n"
            + "}";

    @Test
    @Order(1)
    void testAddMessages() {
        HttpEntity<String> httpEntity = new HttpEntity<>(message14, getHeaders());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(getUrl(),
                httpEntity, String.class);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals(message14, responseEntity.getBody());

        // Adding Message for the next tests
        httpEntity = new HttpEntity<>(message52, getHeaders());
        restTemplate.postForEntity(getUrl(), httpEntity, String.class);
    }

    @Test
    @Order(2)
    void testGetAllMessages() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(), String.class);
        String expectedMessage = "["
                + message14
                + ","
                + message52
                + "]";
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(expectedMessage, responseEntity.getBody());
    }

    @Test
    @Order(3)
    void testGetJsonMessageById() {
        String urlAndId = getUrl() + "/14";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlAndId, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(message14, responseEntity.getBody());
    }

    @Test
    @Order(4)
    void testGetJsonMessageByKeyword() {
        String urlAndKeyword = getUrl() + "/keyword/role";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlAndKeyword, String.class);
        String expectedMessage = "["
                + message52
                + "]";
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(expectedMessage, responseEntity.getBody());
    }

    @Test
    @Order(5)
    void testDeleteRole() {
        restTemplate.delete(getUrl() + "/52");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl(), String.class);
        String expectedMessage = "["
                + message14
                + "]";
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(expectedMessage, responseEntity.getBody());
    }
}
