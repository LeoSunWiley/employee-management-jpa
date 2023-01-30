package com.example.controller;

import com.example.entity.Department;
import com.example.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

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
    @DisplayName("Controller Test - Get all employees")
    public void testGetAllEmployees() throws Exception {

        List<Employee> employees = List.of(employee);
        List<Department> departments = List.of(department);

        when(employeeService.getAllEmployees()).thenReturn(employees);
        when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees"))
                .andExpect(model().attribute("employees", employees))
                .andExpect(model().attribute("departments", departments));
    }

    @Test
    @DisplayName("Controller Test - Get employee")
    public void testGetEmployee() throws Exception {
        List<Department> departments = List.of(department);

        when(employeeService.getEmployee(1)).thenReturn(employee);
        when(departmentService.getAllDepartments()).thenReturn(departments);

        mockMvc.perform(get("/employees/editEmployee").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editEmployee"))
                .andExpect(model().attribute("employee", employee))
                .andExpect(model().attribute("departments", departments));
    }

    @Test
    @DisplayName("Controller Test - Create employee")
    public void testAddEmployee() throws Exception {
        when(departmentService.getDepartment(1)).thenReturn(department);

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();

        List<String> idList = new ArrayList<String>();
        idList.add("1");
        request.put("id", idList);

        List<String> firstNameList = new ArrayList<>();
        firstNameList.add("Grace");
        request.put("firstName", firstNameList);

        List<String> lastNameList = new ArrayList<>();
        lastNameList.add("Hopper");
        request.put("lastName", lastNameList);

        List<String> ageList = new ArrayList<>();
        ageList.add("20");
        request.put("age", ageList);

        List<String> emailList = new ArrayList<>();
        emailList.add("ghopper@inthenavy.us");
        request.put("email", emailList);

        List<String> monthlySalaryList = new ArrayList<>();
        monthlySalaryList.add("1000");
        request.put("monthlySalary", monthlySalaryList);

        List<String> departmentIdList = new ArrayList<>();
        departmentIdList.add("1");
        request.put("departmentId", departmentIdList);

        mockMvc.perform(post("/employees").params(request))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/employees"));
        Mockito.verify(employeeService, Mockito.times(1)).addEmployee(employee);
    }
}
