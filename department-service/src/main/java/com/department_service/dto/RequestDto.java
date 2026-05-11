package com.department_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDto {

    private String name;
    private String description;
    private int code;


}
