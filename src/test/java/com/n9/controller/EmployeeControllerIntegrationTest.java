package com.n9.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String baseUrl;

    @BeforeEach
    public void init() throws Exception {
//        testRestTemplate.postForEntity()
        baseUrl = "http://localhost:" + port + "/employee";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject employeeObject = new JSONObject();
        employeeObject.put("firstName", "nityaay");
        employeeObject.put("lastName", "John");

        HttpEntity<String> request =
                new HttpEntity<String>(employeeObject.toString(), headers);

        this.testRestTemplate.postForEntity(baseUrl, request, String.class);


//        this.testRestTemplate.postForEntity(baseUrl);
    }

    @Test
    @Order(1)
    public void testGetAll() throws Exception {
        String resp = this.testRestTemplate.getForObject(baseUrl,
                String.class);
        JSONArray respJsonObject = new JSONArray(resp);

        assertThat(respJsonObject.getJSONObject(0).get("firstName")).isEqualTo("nityaay");
    }

    @Test
    @Order(2)
    public void testSave() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject employeeObject = new JSONObject();
        employeeObject.put("firstName", "siva");
        employeeObject.put("lastName", "n");

        HttpEntity<String> request =
                new HttpEntity<String>(employeeObject.toString(), headers);

        ResponseEntity<String> resp = this.testRestTemplate.postForEntity(baseUrl, request, String.class);
        Assertions.assertNotNull(resp);
        Assertions.assertNotNull(resp.getStatusCode());
        Assertions.assertTrue(resp.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(resp.getBody());
        String respStr = resp.getBody();
        JSONObject respJsonObject = new JSONObject(respStr);

        assertThat(respJsonObject.get("id")).isNotNull();
        assertThat(respJsonObject.get("firstName")).isEqualTo("siva");
    }

    @Test
    @Order(3)
    public void testGet() throws Exception {
        String resp = this.testRestTemplate.getForObject(baseUrl + "/1",
                String.class);
        JSONObject respJsonObject = new JSONObject(resp);

        assertThat(respJsonObject.get("firstName")).isEqualTo("nityaay");
    }

    @Test
    @Order(4)
    public void testUpdate() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject employeeObject = new JSONObject();
        employeeObject.put("id", "1");
        employeeObject.put("firstName", "siva");
        employeeObject.put("lastName", "n");

        HttpEntity<String> request =
                new HttpEntity<String>(employeeObject.toString(), headers);

        ResponseEntity<String> resp = this.testRestTemplate.exchange(baseUrl, HttpMethod.PUT, request, String.class);
        Assertions.assertNotNull(resp);
        Assertions.assertNotNull(resp.getStatusCode());
        Assertions.assertTrue(resp.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(resp.getBody());
        String respStr = resp.getBody();
        JSONObject respJsonObject = new JSONObject(respStr);

        assertThat(respJsonObject.get("id")).isEqualTo(1);
        assertThat(respJsonObject.get("firstName")).isEqualTo("siva");
    }

    @Test
    @Order(5)
    public void testUpdateWhenIdNotExist() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject employeeObject = new JSONObject();
        employeeObject.put("id", "12");
        employeeObject.put("firstName", "siva");
        employeeObject.put("lastName", "n");

        HttpEntity<String> request =
                new HttpEntity<String>(employeeObject.toString(), headers);

        ResponseEntity<String> resp = this.testRestTemplate.exchange(baseUrl, HttpMethod.PUT, request, String.class);
        Assertions.assertNotNull(resp);
        Assertions.assertNotNull(resp.getStatusCode());
        Assertions.assertTrue(resp.getStatusCode().is5xxServerError());

    }



    @Test
    @Order(6)
    public void testDelete() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> request =
                new HttpEntity<String>(headers);

        ResponseEntity<String> resp = this.testRestTemplate.exchange(baseUrl + "/1", HttpMethod.DELETE, request, String.class);
        Assertions.assertNotNull(resp);
        Assertions.assertNotNull(resp.getStatusCode());
        Assertions.assertTrue(resp.getStatusCode().is2xxSuccessful());

    }


}
