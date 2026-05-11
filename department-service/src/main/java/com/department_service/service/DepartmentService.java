package com.department_service.service;

import com.department_service.dto.RequestDto;
import com.department_service.dto.ResponseDto;
import com.department_service.entity.DepartmentEntity;
import com.department_service.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;


    public ResponseDto addPost(RequestDto dto) {
        DepartmentEntity entity = DepartmentEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .code(dto.getCode())
                .build();
        DepartmentEntity saved = departmentRepository.save(entity);

        return ResponseDto.builder()
                .name(saved.getName())
                .description(saved.getDescription())
                .code(saved.getCode())
                .build();
    }

    public List<ResponseDto> getAll() {
        return departmentRepository.findAll()
                .stream()
                .map(entity -> ResponseDto.builder()
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .code(entity.getCode())
                        .build()
                )
                .toList();
    }

    public ResponseDto getById(Long id) {
        DepartmentEntity entity = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Нету такого!"));

        return ResponseDto.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .code(entity.getCode())
                .build();
    }

    public ResponseDto getByCode(String code) {
        DepartmentEntity entity = departmentRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Отдел с кодом " + code + " не найден"));

        return ResponseDto.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .code(entity.getCode())
                .build();
    }
}