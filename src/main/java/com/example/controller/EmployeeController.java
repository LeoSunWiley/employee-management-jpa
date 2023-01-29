package com.example.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/employees")
public class EmployeeController {

    Set<ConstraintViolation<Employee>> violations = new HashSet<>();

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }
    
    @GetMapping
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        List<Department> departments = departmentService.getAllDepartments();
        
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departments);
        model.addAttribute("errors", violations);
        return "employees";
    }

    @GetMapping("/editEmployee")
    public String getEmployee(Integer id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        List<Department> departments = departmentService.getAllDepartments();

        model.addAttribute("employee", employee);
        model.addAttribute("departments", departments);
        return "editEmployee";
    }

    @PostMapping
    public String addEmployee(Employee employee, HttpServletRequest request, Model model) {
        Integer departmentId = Integer.parseInt(request.getParameter("departmentId"));
        Department department = departmentService.getDepartment(departmentId);
        employee.setDepartment(department);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validator.validate(employee);

        if (violations.isEmpty()) {
            employeeService.addEmployee(employee);
        }

        return "redirect:/employees";
    }

    @PostMapping("/editEmployee")
    public String updateEmployee(@Valid Employee employee, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "editEmployee";
        }

        Integer departmentId = Integer.parseInt(request.getParameter("departmentId"));
        Department department = departmentService.getDepartment(departmentId);
        employee.setDepartment(department);

        employeeService.updateEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("deleteEmployee")
    public String deleteEmployee(Integer id) {
        employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }
}
