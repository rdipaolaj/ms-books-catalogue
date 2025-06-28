package com.unir.missiact1.msbookscatalogue.application.dtos;

import jakarta.validation.constraints.NotBlank;

public class CategoryCreateRequest {
    @NotBlank
    private String name;
    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}