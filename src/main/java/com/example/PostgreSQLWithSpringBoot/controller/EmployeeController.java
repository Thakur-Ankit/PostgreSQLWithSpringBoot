package com.example.PostgreSQLWithSpringBoot.controller;

import com.example.PostgreSQLWithSpringBoot.model.Employee;
import com.example.PostgreSQLWithSpringBoot.repostories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/createEmployee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee emp = employeeRepository.save(employee);
            return new ResponseEntity<>(emp, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employeeList")
    public ResponseEntity<List<Employee>> getEmployeesList() {
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employeeList::add);

        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = null;
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/updateEmployee/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable("id") Long id, @RequestBody Employee employee) {
        Optional<Employee> oldEmp = employeeRepository.findById(id);
        if (oldEmp.isPresent()) {
            Employee emp = oldEmp.get();
            emp.setFirstName(employee.getFirstName());
            emp.setLastName(employee.getLastName());
            emp.setEmail(employee.getEmail());
            emp.setAge(employee.getAge());

            return new ResponseEntity<>(employeeRepository.save(emp), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee Not Found with id : " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long id) {
        employeeRepository.deleteById(id);

        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }
}
