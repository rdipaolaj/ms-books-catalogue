package com.unir.missiact1.msbookscatalogue.controller.v1;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unir.missiact1.msbookscatalogue.application.dtos.BookCreateRequest;
import com.unir.missiact1.msbookscatalogue.application.dtos.BookDto;
import com.unir.missiact1.msbookscatalogue.commons.responses.ApiResponse;
import com.unir.missiact1.msbookscatalogue.commons.responses.ApiResponseHelper;
import com.unir.missiact1.msbookscatalogue.infraestructure.repository.implementations.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ApiResponse<BookDto>> create(@Valid @RequestBody BookCreateRequest req,
                                                       @RequestPart(name="coverImage", required=false) MultipartFile coverImage) throws IOException {
        BookDto created = service.create(req, coverImage);
        ApiResponse<BookDto> body = ApiResponseHelper.createSuccessResponse(created, "Libro creado correctamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PostMapping("/{id}/reserve")
    public ResponseEntity<ApiResponse<String>> reserve(
            @PathVariable UUID id,
            @RequestParam int qty) {
        service.reserveStock(id, qty);
        return ResponseEntity.ok(ApiResponseHelper.createSuccessResponse());
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