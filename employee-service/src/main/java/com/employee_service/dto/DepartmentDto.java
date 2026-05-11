package com.employee_service.dto;

import lombok.*;

// Это зеркало ResponseDto из department-service
// employee-service не зависит от department-service напрямую,
// поэтому делаем свою копию нужных полей

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDto {
    private String name;
    private String description;
    private String code;
}