package com.example.repository;

import com.example.entity.Department;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("Repository Test - Get all departments")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testAllDepartment() {
        List<Department> list = departmentRepository.findAll();

        assertEquals(1, list.size());

        assertEquals(1, list.get(0).getId());
        assertEquals("Sales", list.get(0).getName());
    }

    @Test
    @DisplayName("Repository Test - Get department by id")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testGetDepartmentById() {
        Department department = departmentRepository.findById(1).orElse(null);

        assertEquals(1, department.getId());
        assertEquals("Sales", department.getName());
    }

    @Test
    @DisplayName("Repository Test - Get department by id not exist")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testGetDepartmentByIdWithException() {
        Department department = departmentRepository.findById(2).orElse(null);
        assertNull(department);
    }

    @Test
    @DisplayName("Repository Test - add department")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testAddDepartment() {
        Department department = new Department();
        department.setName("Academy");

        departmentRepository.save(department);

        List<Department> list = departmentRepository.findAll();

        assertEquals(2, list.size());
        assertEquals(2, department.getId());
    }

    @Test
    @DisplayName("Repository Test - update Department")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testUpdateDepartment() {
        Department department = new Department();
        department.setId(1);
        department.setName("Academy");

        departmentRepository.save(department);

        department = departmentRepository.findById(1).orElse(null);

        assertEquals(1, department.getId());
        assertEquals("Academy", department.getName());
    }

    @Test
    @DisplayName("Repository Test - delete department by id")
    @Sql({ "schema.sql", "test-data.sql" })
    public void testDeleteDepartmentById() {
        List<Department> departments = departmentRepository.findAll();
        assertEquals(1, departments.size());
        assertEquals(1, departments.get(0).getId());

        departmentRepository.deleteById(1);

        departments = departmentRepository.findAll();
        assertEquals(0, departments.size());
    }
}
