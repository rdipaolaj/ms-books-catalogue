package com.unir.missiact1.msbookscatalogue.controller.v1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unir.missiact1.msbookscatalogue.application.dtos.CategoryCreateRequest;
import com.unir.missiact1.msbookscatalogue.application.dtos.CategoryDto;
import com.unir.missiact1.msbookscatalogue.commons.responses.ApiResponse;
import com.unir.missiact1.msbookscatalogue.commons.responses.ApiResponseHelper;
import com.unir.missiact1.msbookscatalogue.infraestructure.repository.implementations.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Categorías", description = "Operaciones sobre categorías")
@RestController
@RequestMapping(path = {"/v1/api/categories"}, headers = "X-Api-Version=1")
public class CategoryControllerV1 {
    private final CategoryService service;

    public CategoryControllerV1(CategoryService service) {
        this.service = service;
    }

    @Operation(summary = "Crear categoría", description = "Registra una nueva categoría de libros")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryDto>> create(@Valid @RequestBody CategoryCreateRequest req) {
        CategoryDto created = service.create(req);
        ApiResponse<CategoryDto> body = ApiResponseHelper.createSuccessResponse(created, "Categoría creada correctamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Operation(summary = "Actualizar categoría", description = "Modifica una categoría existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> update(@PathVariable Long id,
                                                            @Valid @RequestBody CategoryDto dto) {
        CategoryDto updated = service.update(id, dto);
        ApiResponse<CategoryDto> body = ApiResponseHelper.createSuccessResponse(updated, "Categoría actualizada correctamente");
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Eliminar categoría", description = "Borra una categoría por su ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        service.delete(id);
        ApiResponse<String> body = ApiResponseHelper.createSuccessResponse(null, "Categoría eliminada correctamente");
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Obtener categoría", description = "Recupera una categoría por su ID")
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> findById(@PathVariable Long id) {
        CategoryDto dto = service.findById(id);
        ApiResponse<CategoryDto> body = ApiResponseHelper.createSuccessResponse(dto);
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Listar categorías", description = "Devuelve todas las categorías")
    @GetMapping("/find-all")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> findAll() {
        List<CategoryDto> list = service.findAll();
        ApiResponse<List<CategoryDto>> body = ApiResponseHelper.createSuccessResponse(list);
        return ResponseEntity.ok(body);
    }
}