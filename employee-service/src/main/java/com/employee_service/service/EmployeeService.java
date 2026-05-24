package com.employee_service.service;


import com.employee_service.dto.DepartmentDto;
import com.employee_service.dto.EmployeeDto;
import com.employee_service.dto.EmployeeWithDepartmentDto;
import com.employee_service.entity.EmployeeEntity;
import com.employee_service.client.DepartmentClient;
import com.employee_service.mapper.EmployeeMapper;
import com.employee_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentClient departmentClient;
    private final EmployeeMapper employeeMapper;


    public List<EmployeeDto> getAll(){
         return employeeRepository.findAll()
                 .stream()
                .map(employeeMapper::toDto) // <- было this::toDto
                .toList();
}

    public EmployeeDto getById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Сотрудник с id " + id + " не найден"));
    }

    public List<EmployeeDto> getByDepartment(String departmentCode) {
        return employeeRepository.findAllByDepartmentCode(departmentCode)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    public EmployeeDto create(EmployeeDto dto) {
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Сотрудник с email " + dto.getEmail() + " уже существует");
        }
        EmployeeEntity saved = employeeRepository.save(employeeMapper.toEntity(dto));  // было toEntity(dto)
        return employeeMapper.toDto(saved);
    }

    public EmployeeDto update(Long id, EmployeeDto dto) {
        EmployeeEntity existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сотрудник с id " + id + " не найден"));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setDepartmentCode(dto.getDepartmentCode());

        return employeeMapper.toDto(employeeRepository.save(existing));  // было toDto(...)
    }

    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Сотрудник с id " + id + " не найден");
        }
        employeeRepository.deleteById(id);
    }

    public EmployeeWithDepartmentDto getWithDepartment(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сотрудник с id " + id + " не найден"));

        // Feign сам делает HTTP запрос
        DepartmentDto department = departmentClient.getByCode(employee.getDepartmentCode());

        return employeeMapper.toWithDepartmentDto(employee, department);
    }
}
