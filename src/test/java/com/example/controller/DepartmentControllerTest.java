package com.example.controller;

import com.example.entity.Department;
import com.example.entity.Employee;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepartmentService departmentService;

    private Employee employee;

    private Department department;

    @BeforeEach
    public void setUp() {
        department = new Department();
        department.setId(1);
        department.setName("Sales");

        employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Grace");
        employee.setLastName("Hopper");
        employee.setDepartment(department);
        employee.setAge(20);
        employee.setEmail("ghopper@inthenavy.us");
        employee.setMonthlySalary(new BigDecimal(1000));

        department.setEmployees(List.of(employee));
    }

    @Test
    @DisplayName("Controller Test - Get all departments")
    public void testGetAllDepartments() throws Exception {
        List<Department> departments = List.of(department);
        when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(view().name("departments"))
                .andExpect(model().attribute("departments", departments));
    }

    @Test
    @DisplayName("Controller Test - Get department")
    public void testGetDepartment() throws Exception {
        when(departmentService.getDepartment(1)).thenReturn(department);

        mockMvc.perform(get("/departments/editDepartment").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editDepartment"))
                .andExpect(model().attribute("department", department));
    }

    @Test
    @DisplayName("Controller Test - Create department")
    public void testAddDepartment() throws Exception {
        mockMvc.perform(post("/departments").params(getMultiValueMap()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/departments"));
        Mockito.verify(departmentService, Mockito.times(1)).addDepartment(department);
    }

    @Test
    @DisplayName("Controller Test - Create department with invalid name")
    public void testAddDepartmentWithInvalidName() throws Exception {
        mockMvc.perform(post("/departments").params(getMultiValueMapWithNameInvalid()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/departments"));
        Mockito.verify(departmentService, Mockito.times(0)).addDepartment(department);
    }

    @Test
    @DisplayName("Controller Test - Update department")
    public void testUpdateDepartment() throws Exception {
        mockMvc.perform(post("/departments/editDepartment").params(getMultiValueMap()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/departments"));
        Mockito.verify(departmentService, Mockito.times(1)).updateDepartment(department);
    }

    @Test
    @DisplayName("Controller Test - Update department with invalid name")
    public void testUpdateEmployeeWithInvalidAge() throws Exception {
        mockMvc.perform(post("/departments/editDepartment").params(getMultiValueMapWithNameInvalid()))
                .andExpect(status().isOk())
                .andExpect(view().name("editDepartment"));
        Mockito.verify(departmentService, Mockito.times(0)).updateDepartment(department);
    }

    @Test
    @DisplayName("Controller Test - Delete department")
    public void testDeleteDepartment() throws Exception {
        doNothing().when(departmentService).deleteDepartmentById(1);

        mockMvc.perform(get("/departments/deleteDepartment").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/departments"));
        Mockito.verify(departmentService, times(1)).deleteDepartmentById(1);
    }

    @Test
    @DisplayName("Controller Test - Get department detail")
    public void testGetDepartmentDetail() throws Exception {
        List<Employee> employees = List.of(employee);

        when(departmentService.getDepartment(1)).thenReturn(department);
        when(employeeService.findEmployeeeByDepartment(department)).thenReturn(employees);

        mockMvc.perform(get("/departments/departmentDetail").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("departmentDetail"))
                .andExpect(model().attribute("department", department))
                .andExpect(model().attribute("employees", employees));
    }

    private static MultiValueMap<String, String> getMultiValueMap() {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();

        List<String> idList = new ArrayList<>();
        idList.add("1");
        request.put("id", idList);

        List<String> nameList = new ArrayList<>();
        nameList.add("Sales");
        request.put("name", nameList);

        return request;
    }

    private static MultiValueMap<String, String> getMultiValueMapWithNameInvalid() {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();

        List<String> idList = new ArrayList<>();
        idList.add("1");
        request.put("id", idList);

        List<String> nameList = new ArrayList<>();
        nameList.add("");
        request.put("name", nameList);

        return request;
    }
}
