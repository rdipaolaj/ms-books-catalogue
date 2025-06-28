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

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = {"/v1/api/authors"}, headers = "X-Api-Version=1")
public class AuthorControllerV1 {
    private final AuthorService service;

    public AuthorControllerV1(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDto>> create(@Valid @RequestBody AuthorCreateRequest req) {
        AuthorDto created = service.create(req);
        ApiResponse<AuthorDto> body = ApiResponseHelper.createSuccessResponse(created, "Autor creado correctamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorDto>> update(@PathVariable Long id,
                                                         @Valid @RequestBody AuthorDto dto) {
        AuthorDto updated = service.update(id, dto);
        ApiResponse<AuthorDto> body = ApiResponseHelper.createSuccessResponse(updated, "Autor actualizado correctamente");
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        service.delete(id);
        ApiResponse<String> body = ApiResponseHelper.createSuccessResponse(null, "Autor eliminado correctamente");
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorDto>> findById(@PathVariable Long id) {
        AuthorDto dto = service.findById(id);
        ApiResponse<AuthorDto> body = ApiResponseHelper.createSuccessResponse(dto);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorDto>>> findAll() {
        List<AuthorDto> list = service.findAll();
        ApiResponse<List<AuthorDto>> body = ApiResponseHelper.createSuccessResponse(list);
        return ResponseEntity.ok(body);
    }
}