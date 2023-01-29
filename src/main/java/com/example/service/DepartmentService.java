package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Department;
import com.example.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(int id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public void updateDepartment(Department department) {
        departmentRepository.save(department);
    }

    public void deleteDepartmentById(int id) {
        departmentRepository.deleteById(id);
    }
}
