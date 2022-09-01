package com.n9.controller;

import com.n9.service.EmployeeService;
import com.n9.model.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity save(@RequestBody EmployeeDTO employeeDTO) {

        EmployeeDTO savedEmployeeDTO = employeeService.save(employeeDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeDTO);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody EmployeeDTO employeeDTO) {

        EmployeeDTO savedEmployeeDTO = employeeService.update(employeeDTO);

        return ResponseEntity.status(HttpStatus.OK).body(savedEmployeeDTO);
    }


    @GetMapping
    public ResponseEntity getAll() {

        List<EmployeeDTO> employeeDTOList = employeeService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(employeeDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {

        EmployeeDTO employeeDTO = employeeService.get(id);

        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity remove(@PathVariable("id") Long id) {

        employeeService.remove(id);

        return ResponseEntity.noContent().build();
    }
}
