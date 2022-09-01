package com.n9.service;

import com.n9.model.EmployeeDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    private EmployeeDTO getEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("nityaay");
        employeeDTO.setLastName("n");
        employeeDTO.setId(1l);

        return employeeDTO;
    }

    @BeforeEach
    public void init() throws Exception {
        employeeService.save(getEmployeeDTO());
    }

    @Test
    @Order(1)
    public void testGetAll() throws Exception {
        List<EmployeeDTO> employeeDTOList = employeeService.getAll();

        Assertions.assertNotNull(employeeDTOList);
        Assertions.assertTrue(employeeDTOList.size() > 0);
        Assertions.assertEquals("nityaay", employeeDTOList.get(0).getFirstName());
    }

    @Test
    @Order(2)
    public void testSave() throws Exception {
        EmployeeDTO employeeDTO = getEmployeeDTO();
        employeeDTO.setFirstName("siva");
        EmployeeDTO savedEmployeeDTO = employeeService.save(employeeDTO);
        Assertions.assertNotNull(savedEmployeeDTO);

        Assertions.assertEquals("siva", savedEmployeeDTO.getFirstName());

    }

    @Test
    @Order(3)
    public void testUpdate() throws Exception {
        EmployeeDTO employeeDTO = getEmployeeDTO();
        employeeDTO.setId(1l);
        employeeDTO.setFirstName("siva");
        EmployeeDTO savedEmployeeDTO = employeeService.update(employeeDTO);
        Assertions.assertNotNull(savedEmployeeDTO);

        Assertions.assertEquals(1l, savedEmployeeDTO.getId());
        Assertions.assertEquals("siva", savedEmployeeDTO.getFirstName());

    }

    @Test
    @Order(4)
    public void testUpdateWhenEmployeeNotFound() throws Exception {
        EmployeeDTO employeeDTO = getEmployeeDTO();
        employeeDTO.setId(12l);

        Exception e = Assertions.assertThrows(RuntimeException.class, () -> {
            EmployeeDTO savedEmployeeDTO = employeeService.update(employeeDTO);
        });

        Assertions.assertEquals("Employee not found", e.getMessage());

    }

    @Test
    @Order(5)
    public void testDelete() throws Exception {

        employeeService.remove(1l);

        EmployeeDTO employeeDTO = employeeService.get(1l);

        Assertions.assertNull(employeeDTO);

    }
}
