DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;

CREATE TABLE department
(
    id   INT PRIMARY KEY auto_increment,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE employee
(
    id             INT PRIMARY KEY auto_increment,
    first_name     VARCHAR(50)  NOT NULL,
    last_name      VARCHAR(50)  NOT NULL,
    age            INT          NOT NULL CHECK (
        age BETWEEN 18 AND 70
        ),
    email          VARCHAR(256) NOT NULL,
    monthly_salary DECIMAL      NOT NULL,
    department_id  INT,
    FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE SET NULL
);