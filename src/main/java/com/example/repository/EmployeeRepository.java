package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Department;
import com.example.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
    List<Employee> findByDepartment(Department department);
}
