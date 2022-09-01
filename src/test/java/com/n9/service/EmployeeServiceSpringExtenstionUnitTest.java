package com.n9.service;

import com.n9.entity.Employee;
import com.n9.model.EmployeeDTO;
import com.n9.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = {EmployeeService.class, ModelMapper.class}
)
public class EmployeeServiceSpringExtenstionUnitTest {


    @Autowired
    private EmployeeService employeeService;


    @MockBean
    private EmployeeRepository employeeRepository;


    private Employee getEmployeeEntity() {
        Employee employee = new Employee();
        employee.setFirstName("nityaay");
        employee.setLastName("n");
        employee.setId(1l);

        return employee;
    }

    private EmployeeDTO getEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("nityaay");
        employeeDTO.setLastName("n");


        return employeeDTO;
    }

    @BeforeEach
    public void init() throws Exception {


    }

    @Test

    public void testGetAll() throws Exception {

        List<Employee> employeeEntityList = new ArrayList<Employee>();
        Employee employee = getEmployeeEntity();
        employeeEntityList.add(employee);

        //mock the method call
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeEntityList);

        List<EmployeeDTO> employeeDTOList = employeeService.getAll();

        Assertions.assertNotNull(employeeDTOList);
        Assertions.assertTrue(employeeDTOList.size() > 0);
        Assertions.assertEquals("nityaay", employeeDTOList.get(0).getFirstName());

        // for verification
        Mockito.verify(employeeRepository).findAll();

        // Mockito.verify(modelMapper).map(Mockito.any(Employee.class), Mockito.any());
    }

    @Test

    public void testSave() throws Exception {
        EmployeeDTO employeeDTO = getEmployeeDTO();

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(getEmployeeEntity());

        EmployeeDTO savedEmployeeDTO = employeeService.save(employeeDTO);
        Assertions.assertNotNull(savedEmployeeDTO);

        // for verification
        Mockito.verify(employeeRepository).save(Mockito.any(Employee.class));

    }

    @Test

    public void testUpdate() throws Exception {
        EmployeeDTO employeeDTO = getEmployeeDTO();
        employeeDTO.setId(2l);

        Employee employee = getEmployeeEntity();

        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(getEmployeeEntity());
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));

        EmployeeDTO updatedEmployeeDTO = employeeService.update(employeeDTO);
        Assertions.assertNotNull(updatedEmployeeDTO);

        // for verification
        Mockito.verify(employeeRepository).save(Mockito.any(Employee.class));
        Mockito.verify(employeeRepository).findById(Mockito.anyLong());
    }

    @Test
    public void testUpdateWhenEmployeeNotFound() throws Exception {
        EmployeeDTO employeeDTO = getEmployeeDTO();
        employeeDTO.setId(5l);

        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Exception ex = Assertions.assertThrows(RuntimeException.class, () -> {
            EmployeeDTO updatedEmployeeDTO = employeeService.update(employeeDTO);
        });

        Assertions.assertEquals("Employee not found", ex.getMessage());

        //for verification
        Mockito.verify(employeeRepository).findById(Mockito.anyLong());

    }

    @Test
    public void testDelete() throws Exception {
        Mockito.doNothing().when(employeeRepository).deleteById(Mockito.anyLong());
        employeeService.remove(5l);

        //for verification
        Mockito.verify(employeeRepository).deleteById(Mockito.anyLong());
    }

}
