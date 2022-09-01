package com.n9.service;

import com.n9.entity.Employee;
import com.n9.model.EmployeeDTO;
import com.n9.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDTO save(EmployeeDTO employeeDTO) {

        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee = employeeRepository.save(employee);
        EmployeeDTO savedEmployeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        return savedEmployeeDTO;
    }

    public EmployeeDTO saveAll(List<EmployeeDTO> employeeDTOList) {

        List<Employee> employeeList=new ArrayList<>();
        for (EmployeeDTO employeeDTO:employeeDTOList) {
            Employee employee = modelMapper.map(employeeDTO, Employee.class);
            employeeList.add(employee);
        }
       employeeRepository.saveAll(employeeList);

        return null;
    }
    public EmployeeDTO update(EmployeeDTO employeeDTO) {

        Optional<Employee> existingEmp = employeeRepository.findById(employeeDTO.getId());
        if (existingEmp.isPresent()) {

            Employee employee = existingEmp.get();
            modelMapper.map(employeeDTO, employee);
            employee = employeeRepository.save(employee);
            EmployeeDTO savedEmployeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        } else {
            throw new RuntimeException("Employee not found");
        }
        return employeeDTO;
    }

    public EmployeeDTO get(Long empId) {
        EmployeeDTO employeeDTO = null;
        Optional<Employee> existingEmp = employeeRepository.findById(empId);
        if (existingEmp.isPresent()) {
            employeeDTO = modelMapper.map(existingEmp.get(), EmployeeDTO.class);
        }
        return employeeDTO;
    }

    public void remove(Long empId) {
        employeeRepository.deleteById(empId);
    }

    public List<EmployeeDTO> getAll() {
        List<EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>();
        for (Employee employee : employeeRepository.findAll()) {
            employeeDTOList.add(modelMapper.map(employee, EmployeeDTO.class));
        }

        return employeeDTOList;
    }


}
