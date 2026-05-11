package com.employee_service.service;


import com.employee_service.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-service", url = "http://localhost:8081")
public interface DepartmentClient {

    @GetMapping("/departments/code/{code}")
    DepartmentDto getByCode(@PathVariable String code);
}