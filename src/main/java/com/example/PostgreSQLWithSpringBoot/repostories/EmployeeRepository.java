package com.example.PostgreSQLWithSpringBoot.repostories;

import com.example.PostgreSQLWithSpringBoot.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    public void deleteById(Long id);

}
