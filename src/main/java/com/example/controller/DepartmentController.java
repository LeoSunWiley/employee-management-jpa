package com.example.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Department;
import com.example.entity.Employee;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    Set<ConstraintViolation<Department>> violations = new HashSet<>();

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getAllDepartments(Model model) {
        List<Department> departments = departmentService.getAllDepartments();
        
        model.addAttribute("departments", departments);
        model.addAttribute("errors", violations);
        return "departments";
    }

    @GetMapping("/editDepartment")
    public String getDepartment(Integer id, Model model) {
        Department department = departmentService.getDepartment(id);
        model.addAttribute("department", department);
        return "editDepartment";
    }

    @PostMapping
    public String addDepartment(Department department, Model model) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validator.validate(department);

        if (violations.isEmpty()) {
            departmentService.addDepartment(department);
        }

        return "redirect:/departments";
    }

    @PostMapping("/editDepartment")
    public String updateDepartment(@Valid Department department, BindingResult result) {
        if (result.hasErrors()) {
            return "editDepartment";
        }

        departmentService.updateDepartment(department);
        return "redirect:/departments";
    }

    @GetMapping("deleteDepartment")
    public String deleteDepartment(Integer id) {
        departmentService.deleteDepartmentById(id);
        return "redirect:/departments";
    }

    @GetMapping("departmentDetail")
    public String departmentDetail(Integer id, Model model) {
        Department department = departmentService.getDepartment(id);
        List<Employee> employees = employeeService.findEmployeeeByDepartment(department);

        model.addAttribute("department", department);
        model.addAttribute("employees", employees);

        return "departmentDetail";
    }
}
