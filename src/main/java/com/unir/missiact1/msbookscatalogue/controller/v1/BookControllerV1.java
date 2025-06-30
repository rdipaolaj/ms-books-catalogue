package com.unir.missiact1.msbookscatalogue.controller.v1;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unir.missiact1.msbookscatalogue.application.dtos.BookCreateRequest;
import com.unir.missiact1.msbookscatalogue.application.dtos.BookDto;
import com.unir.missiact1.msbookscatalogue.commons.responses.ApiResponse;
import com.unir.missiact1.msbookscatalogue.commons.responses.ApiResponseHelper;
import com.unir.missiact1.msbookscatalogue.infraestructure.repository.implementations.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Libros", description = "Operaciones sobre libros")
@RestController
@RequestMapping(path = {"/v1/api/books"}, headers = "X-Api-Version=1")
public class BookControllerV1 {
    private final BookService service;

    public BookControllerV1(BookService service) {
        this.service = service;
    }

    @Operation(summary = "Crear libro", description = "Registra un nuevo libro en el catálogo")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BookDto>> create(@Valid @RequestBody BookCreateRequest req) {
        BookDto created = service.create(req);
        ApiResponse<BookDto> body = ApiResponseHelper.createSuccessResponse(created, "Libro creado correctamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Operation(summary = "Actualizar libro", description = "Actualiza todos los campos de un libro")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<BookDto>> update(@PathVariable UUID id,
                                                       @Valid @RequestBody BookDto dto) {
        BookDto updated = service.update(id, dto);
        ApiResponse<BookDto> body = ApiResponseHelper.createSuccessResponse(updated, "Libro actualizado correctamente");
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Parchear libro", description = "Aplica actualizaciones parciales a un libro")
    @PatchMapping("/patch/{id}")
    public ResponseEntity<ApiResponse<BookDto>> patch(@PathVariable UUID id,
                                                      @RequestBody Map<String, Object> updates) {
        BookDto patched = service.patch(id, updates);
        ApiResponse<BookDto> body = ApiResponseHelper.createSuccessResponse(patched, "Libro parcheado correctamente");
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Eliminar libro", description = "Borra un libro por su UUID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        service.delete(id);
        ApiResponse<String> body = ApiResponseHelper.createSuccessResponse(null, "Libro eliminado correctamente");
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Obtener libro", description = "Recupera un libro por su UUID")
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponse<BookDto>> findById(@PathVariable UUID id) {
        BookDto dto = service.findById(id);
        ApiResponse<BookDto> body = ApiResponseHelper.createSuccessResponse(dto);
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Buscar libros", description = "Filtra libros por título y/o autor")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookDto>>> search(@RequestParam Optional<String> title,
                                                             @RequestParam Optional<Long> authorId) {
        List<BookDto> results = service.search(title, authorId);
        ApiResponse<List<BookDto>> body = ApiResponseHelper.createSuccessResponse(results);
        return ResponseEntity.ok(body);
    }
}