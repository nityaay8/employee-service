package com.n9.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    String baseUrl;

    @BeforeEach
    public void init() throws Exception {
//        testRestTemplate.postForEntity()
        baseUrl = "/employee";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject employeeObject = new JSONObject();
        employeeObject.put("firstName", "nityaay");
        employeeObject.put("lastName", "John");

        HttpEntity<String> request =
                new HttpEntity<String>(employeeObject.toString(), headers);

        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .content(employeeObject.toString())).andDo(print()).andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(1)
    public void testGetAll() throws Exception {
        this.mockMvc.perform(get(baseUrl)).andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("nityaay")));
    }

    @Test
    @Order(2)
    public void testSave() throws Exception {
        JSONObject employeeObject = new JSONObject();
        employeeObject.put("firstName", "mahesh");
        employeeObject.put("lastName", "N");
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(employeeObject.toString())).andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("mahesh")));
    }
}
