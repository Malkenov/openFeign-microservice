package com.department_service.controller;

import com.department_service.dto.RequestDto;
import com.department_service.dto.ResponseDto;
import com.department_service.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    @PostMapping
    public ResponseDto create(@RequestBody RequestDto dto) {
        return service.addPost(dto);
    }

    @GetMapping
    public List<ResponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/code/{code}")
    public ResponseDto getByCode(@PathVariable String code) {
        return service.getByCode(code);
    }
}
