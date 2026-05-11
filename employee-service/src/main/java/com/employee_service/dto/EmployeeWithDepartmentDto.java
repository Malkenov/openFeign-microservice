package com.employee_service.dto;

import lombok.*;

// Возвращается клиенту: данные сотрудника + данные его отдела
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeWithDepartmentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private DepartmentDto department;
}