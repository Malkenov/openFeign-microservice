package com.employee_service.service;


import com.employee_service.dto.DepartmentDto;
import com.employee_service.dto.EmployeeDto;
import com.employee_service.dto.EmployeeWithDepartmentDto;
import com.employee_service.entity.EmployeeEntity;
import com.employee_service.repository.DepartmentClient;
import com.employee_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentClient departmentClient;

    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public EmployeeDto getById(Long id) {
        return employeeRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Сотрудник с id " + id + " не найден"));
    }

    public List<EmployeeDto> getByDepartment(String departmentCode) {
        return employeeRepository.findAllByDepartmentCode(departmentCode)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public EmployeeDto create(EmployeeDto dto) {
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Сотрудник с email " + dto.getEmail() + " уже существует");
        }
        EmployeeEntity saved = employeeRepository.save(toEntity(dto));
        return toDto(saved);
    }

    public EmployeeDto update(Long id, EmployeeDto dto) {
        EmployeeEntity existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сотрудник с id " + id + " не найден"));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setDepartmentCode(dto.getDepartmentCode());

        return toDto(employeeRepository.save(existing));
    }

    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Сотрудник с id " + id + " не найден");
        }
        employeeRepository.deleteById(id);
    }

    //Маппинг

    private EmployeeDto toDto(EmployeeEntity entity) {
        return EmployeeDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .departmentCode(entity.getDepartmentCode())
                .build();
    }

    private EmployeeEntity toEntity(EmployeeDto dto) {
        return EmployeeEntity.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .departmentCode(dto.getDepartmentCode())
                .build();
    }

    public EmployeeWithDepartmentDto getWithDepartment(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сотрудник с id " + id + " не найден"));

        // Feign сам делает HTTP запрос
        DepartmentDto department = departmentClient.getByCode(employee.getDepartmentCode());

        return EmployeeWithDepartmentDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .department(department)
                .build();
    }
}
