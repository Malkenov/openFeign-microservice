package com.employee_service.mapper;

import com.employee_service.dto.DepartmentDto;
import com.employee_service.dto.EmployeeDto;
import com.employee_service.dto.EmployeeWithDepartmentDto;
import com.employee_service.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto toDto(EmployeeEntity entity);

    EmployeeEntity toEntity(EmployeeDto dto);

    @Mapping(target = "department", source = "department")
    EmployeeWithDepartmentDto toWithDepartmentDto(EmployeeEntity entity, DepartmentDto department);
}
