package com.example.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "First name must not be empty")
    @Size(max = 50, message = "First name must be less than 50 characters")
    @Column
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    @Column
    private String lastName;

    @NotNull(message = "Age must not be null")
    @Min(value = 18, message = "Age must be grater than or equal to 18")
    @Max(value = 70, message = "Age must be less than or equal to 70")
    @Column
    private Integer age;

    @NotBlank(message = "Email must not be empty")
    @Size(max = 255, message = "Email must be less than 255 characters")
    @Column
    private String email;

    @NotNull(message = "monthly salary must not be null")
    @Column
    private BigDecimal monthlySalary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(BigDecimal monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
