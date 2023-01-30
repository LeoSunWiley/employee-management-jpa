package com.example.repository;

import com.example.entity.Department;
import com.example.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("Repository Test - Get all employees")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testAllEmployee() {
        List<Employee> list = employeeRepository.findAll();

        assertEquals(1, list.size());

        assertEquals(1, list.get(0).getId());
        assertEquals("Grace", list.get(0).getFirstName());
        assertEquals("Hopper", list.get(0).getLastName());
        assertEquals(20, list.get(0).getAge());
        assertEquals("ghopper@inthenavy.us", list.get(0).getEmail());
        assertEquals(new BigDecimal(1000), list.get(0).getMonthlySalary());
    }

    @Test
    @DisplayName("Repository Test - Get employee by id")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testGetEmployeeById() {
        Employee employee = employeeRepository.findById(1).orElse(null);

        assertEquals(1, employee.getId());
        assertEquals("Grace", employee.getFirstName());
        assertEquals("Hopper", employee.getLastName());
        assertEquals(20, employee.getAge());
        assertEquals("ghopper@inthenavy.us", employee.getEmail());
        assertEquals(new BigDecimal(1000), employee.getMonthlySalary());
    }

    @Test
    @DisplayName("Repository Test - Get employee by id not exist")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testGetEmployeeByIdWithException() {
        Employee employee = employeeRepository.findById(2).orElse(null);
        assertNull(employee);
    }

    @Test
    @DisplayName("Repository Test - add employee")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testAddEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Jack");
        employee.setLastName("Lee");
        employee.setAge(30);
        employee.setEmail("jack@example.com");
        employee.setMonthlySalary(new BigDecimal(2000));

        employeeRepository.save(employee);

        List<Employee> list = employeeRepository.findAll();

        assertEquals(2, list.size());
        assertEquals(2, employee.getId());
    }

    @Test
    @DisplayName("Repository Test - update employee")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Grace");
        employee.setLastName("Hopper");
        employee.setAge(30);
        employee.setEmail("ghopper@inthenavy.com");
        employee.setMonthlySalary(new BigDecimal(2000));

        employeeRepository.save(employee);

        employee = employeeRepository.findById(1).orElse(null);

        assertEquals(1, employee.getId());
        assertEquals("Grace", employee.getFirstName());
        assertEquals("Hopper", employee.getLastName());
        assertEquals(30, employee.getAge());
        assertEquals("ghopper@inthenavy.com", employee.getEmail());
        assertEquals(new BigDecimal(2000), employee.getMonthlySalary());
    }

    @Test
    @DisplayName("Repository Test - delete employee by id")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testDeleteEmployeeById() {
        List<Employee> employees = employeeRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals(1, employees.get(0).getId());

        employeeRepository.deleteById(1);

        employees = employeeRepository.findAll();
        assertEquals(0, employees.size());
    }

    @Test
    @DisplayName("Repository Test - find employees by whose department")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testFindByDepartment() {
        Department department = departmentRepository.findById(1).orElse(null);
        List<Employee> employees = employeeRepository.findByDepartment(department);

        assertEquals(1, employees.size());

        assertEquals(1, employees.get(0).getId());
        assertEquals("Grace", employees.get(0).getFirstName());
        assertEquals("Hopper", employees.get(0).getLastName());
        assertEquals(20, employees.get(0).getAge());
        assertEquals("ghopper@inthenavy.us", employees.get(0).getEmail());
        assertEquals(new BigDecimal(1000), employees.get(0).getMonthlySalary());
    }
}
