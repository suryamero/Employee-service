package com.employee.service.service;

import com.employee.service.exception.DuplicateResourceException;
import com.employee.service.exception.ResourceNotFoundException;
import com.employee.service.model.Employee;
import com.employee.service.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    
    @Mock
    private EmployeeRepository employeeRepository;
    
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    
    private Employee employee;
    
    @BeforeEach
    void setUp() {
        employee = new Employee("John", "Doe", "john.doe@example.com", "IT", 75000.0);
        employee.setId(1L);
    }
    
    @Test
    void testCreateEmployee_Success() {
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        
        Employee createdEmployee = employeeService.createEmployee(employee);
        
        assertNotNull(createdEmployee);
        assertEquals("john.doe@example.com", createdEmployee.getEmail());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
    
    @Test
    void testCreateEmployee_DuplicateEmail() {
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);
        
        assertThrows(DuplicateResourceException.class, () -> {
            employeeService.createEmployee(employee);
        });
        
        verify(employeeRepository, never()).save(any(Employee.class));
    }
    
    @Test
    void testGetEmployeeById_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        
        Employee foundEmployee = employeeService.getEmployeeById(1L);
        
        assertNotNull(foundEmployee);
        assertEquals(1L, foundEmployee.getId());
        verify(employeeRepository, times(1)).findById(1L);
    }
    
    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(999L);
        });
    }
    
    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(employee);
        when(employeeRepository.findAll()).thenReturn(employees);
        
        List<Employee> result = employeeService.getAllEmployees();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAll();
    }
    
    @Test
    void testUpdateEmployee_Success() {
        Employee updatedData = new Employee("John", "Doe", "john.updated@example.com", "Engineering", 85000.0);
        
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsByEmail(updatedData.getEmail())).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        
        Employee result = employeeService.updateEmployee(1L, updatedData);
        
        assertNotNull(result);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
    
    @Test
    void testUpdateEmployee_NotFound() {
        Employee updatedData = new Employee("John", "Doe", "john.updated@example.com", "Engineering", 85000.0);
        
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(999L, updatedData);
        });
    }
    
    @Test
    void testDeleteEmployee_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);
        
        employeeService.deleteEmployee(1L);
        
        verify(employeeRepository, times(1)).delete(employee);
    }
    
    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(999L);
        });
    }
    
    @Test
    void testGetEmployeeByEmail_Success() {
        when(employeeRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(employee));
        
        Employee result = employeeService.getEmployeeByEmail("john.doe@example.com");
        
        assertNotNull(result);
        assertEquals("john.doe@example.com", result.getEmail());
    }
    
    @Test
    void testGetEmployeeByEmail_NotFound() {
        when(employeeRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeByEmail("notfound@example.com");
        });
    }
}
