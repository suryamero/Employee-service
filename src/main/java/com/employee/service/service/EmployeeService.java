package com.employee.service.service;

import com.employee.service.model.Employee;

import java.util.List;

public interface EmployeeService {
    
    Employee createEmployee(Employee employee);
    
    Employee getEmployeeById(Long id);
    
    List<Employee> getAllEmployees();
    
    Employee updateEmployee(Long id, Employee employee);
    
    void deleteEmployee(Long id);
    
    Employee getEmployeeByEmail(String email);
}
