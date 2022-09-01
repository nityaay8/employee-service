package com.n9.controller;

import com.n9.model.EmployeeDTO;
import com.n9.service.EmployeeService;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerWebLayerUnitTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private EmployeeService employeeService;

    String baseUrl;

    @BeforeEach
    public void init() throws Exception {
//        testRestTemplate.postForEntity()
        baseUrl = "/employee";

    }


    private EmployeeDTO getEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("nityaay");
        employeeDTO.setLastName("n");
        employeeDTO.setId(1l);

        return employeeDTO;
    }

    @Test
    public void testGetAll() throws Exception {


        List<EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>();
        employeeDTOList.add(getEmployeeDTO());

        //mock the method call
        Mockito.when(employeeService.getAll()).thenReturn(employeeDTOList);


        this.mockMvc.perform(get(baseUrl)).andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("nityaay")));

        //for verification of method call
        Mockito.verify(employeeService).getAll();
    }

    @Test
    public void testSave() throws Exception {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("siva");
        employeeDTO.setLastName("n");
        employeeDTO.setId(2l);

        //mock the method call
        Mockito.when(employeeService.save(Mockito.any(EmployeeDTO.class))).thenReturn(employeeDTO);

        JSONObject employeeObject = new JSONObject();
        employeeObject.put("firstName", "siva");
        employeeObject.put("lastName", "N");
        this.mockMvc.perform(post(baseUrl).content(employeeObject.toString()).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("siva")));

        //for verification of method call
        Mockito.verify(employeeService, Mockito.times(1)).save(Mockito.any(EmployeeDTO.class));
    }

    @Test
    public void testGet() throws Exception {

        //mock the method call
        Mockito.when(employeeService.get(Mockito.anyLong())).thenReturn(getEmployeeDTO());

        this.mockMvc.perform(get(baseUrl + "/1")).andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("nityaay")));

        //for verification of method call
        Mockito.verify(employeeService, Mockito.times(1)).get(Mockito.anyLong());

    }


    @Test
    public void testUpdate() throws Exception {

        JSONObject employeeObject = new JSONObject();
        employeeObject.put("id", "1");
        employeeObject.put("firstName", "siva");
        employeeObject.put("lastName", "n");
        EmployeeDTO mockEmployeeDTO = getEmployeeDTO();
        mockEmployeeDTO.setFirstName("siva");

        //mock the method call
        Mockito.when(employeeService.update(Mockito.any(EmployeeDTO.class))).thenReturn(mockEmployeeDTO);

        this.mockMvc.perform(put(baseUrl).content(employeeObject.toString()).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("siva")));

        //for verification of method call
        Mockito.verify(employeeService, Mockito.times(1)).update(Mockito.any(EmployeeDTO.class));
    }


    @Test
    public void testUpdateWhenIdNotExist() throws Exception {

        JSONObject employeeObject = new JSONObject();
        employeeObject.put("id", "12");
        employeeObject.put("firstName", "siva");
        employeeObject.put("lastName", "n");

        //mock the method call
        Mockito.doThrow(new RuntimeException("Employee not found")).when(employeeService).
                update(Mockito.any(EmployeeDTO.class));

        Exception re = Assertions.assertThrows(Exception.class, () -> {
            this.mockMvc.perform(put(baseUrl).content(employeeObject.toString()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                    .andExpect(status().is5xxServerError());
        });

        //for verification of method call
        Mockito.verify(employeeService, Mockito.times(1)).update(Mockito.any(EmployeeDTO.class));

        Assertions.assertTrue(re.getMessage().contains("Employee not found"));

    }


    @Test
    public void testDelete() throws Exception {

        //mock the method call
        Mockito.doNothing().when(employeeService).remove(Mockito.anyLong());

        this.mockMvc.perform(delete(baseUrl + "/1")).andDo(print()).andExpect(status().is2xxSuccessful());

        //for verification of method call
        Mockito.verify(employeeService, Mockito.times(1)).remove(Mockito.anyLong());

    }
}
