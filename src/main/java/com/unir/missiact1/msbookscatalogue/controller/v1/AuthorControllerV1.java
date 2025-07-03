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

import com.unir.missiact1.msbookscatalogue.application.dtos.AuthorCreateRequest;
import com.unir.missiact1.msbookscatalogue.application.dtos.AuthorDto;
import com.unir.missiact1.msbookscatalogue.commons.responses.ApiResponse;
import com.unir.missiact1.msbookscatalogue.commons.responses.ApiResponseHelper;
import com.unir.missiact1.msbookscatalogue.infraestructure.repository.implementations.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Autores", description = "Operaciones sobre autores")
@RestController
@RequestMapping(path = {"/v1/api/authors"}, headers = "X-Api-Version=1")
public class AuthorControllerV1 {
    private final AuthorService service;

    public AuthorControllerV1(AuthorService service) {
        this.service = service;
    }

    @Operation(summary = "Crear autor", description = "Registra un nuevo autor en el catálogo")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AuthorDto>> create(@Valid @RequestBody AuthorCreateRequest req) {
        AuthorDto created = service.create(req);
        ApiResponse<AuthorDto> body = ApiResponseHelper.createSuccessResponse(created, "Autor creado correctamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Operation(summary = "Actualizar autor", description = "Modifica los datos de un autor existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AuthorDto>> update(@PathVariable Long id,
                                                         @Valid @RequestBody AuthorDto dto) {
        AuthorDto updated = service.update(id, dto);
        ApiResponse<AuthorDto> body = ApiResponseHelper.createSuccessResponse(updated, "Autor actualizado correctamente");
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Eliminar autor", description = "Borra un autor por su identificador")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        service.delete(id);
        ApiResponse<String> body = ApiResponseHelper.createSuccessResponse(null, "Autor eliminado correctamente");
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Obtener autor", description = "Recupera un autor por su ID")
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse<AuthorDto>> findById(@PathVariable Long id) {
        AuthorDto dto = service.findById(id);
        ApiResponse<AuthorDto> body = ApiResponseHelper.createSuccessResponse(dto);
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Listar autores", description = "Devuelve todos los autores del catálogo")
    @GetMapping("/find-all")
    public ResponseEntity<ApiResponse<List<AuthorDto>>> findAll() {
        List<AuthorDto> list = service.findAll();
        ApiResponse<List<AuthorDto>> body = ApiResponseHelper.createSuccessResponse(list);
        return ResponseEntity.ok(body);
    }
}