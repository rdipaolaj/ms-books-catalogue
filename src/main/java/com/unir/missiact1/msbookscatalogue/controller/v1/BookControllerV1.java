package com.unir.missiact1.msbookscatalogue.controller.v1;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.unir.missiact1.msbookscatalogue.application.dtos.BookSearchRequest;
import com.unir.missiact1.msbookscatalogue.application.request.mapster.BookMapper;
import com.unir.missiact1.msbookscatalogue.commons.responses.PageResponse;
import com.unir.missiact1.msbookscatalogue.domain.BookDocument;
import com.unir.missiact1.msbookscatalogue.infraestructure.repository.implementations.BookSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = {"/v1/api/books"}, headers = "X-Api-Version=1")
public class BookControllerV1 {
    private final BookService service;
    private final BookSearchService searchService;


    public BookControllerV1(BookService service, BookSearchService searchService) {
        this.service = service;
        this.searchService = searchService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookDto>> create(@Valid @RequestBody BookCreateRequest req) {
        BookDto created = service.create(req);
        ApiResponse<BookDto> body = ApiResponseHelper.createSuccessResponse(created, "Libro creado correctamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> update(@PathVariable UUID id,
                                                       @Valid @RequestBody BookDto dto) {
        BookDto updated = service.update(id, dto);
        ApiResponse<BookDto> body = ApiResponseHelper.createSuccessResponse(updated, "Libro actualizado correctamente");
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> patch(@PathVariable UUID id,
                                                      @RequestBody Map<String, Object> updates) {
        BookDto patched = service.patch(id, updates);
        ApiResponse<BookDto> body = ApiResponseHelper.createSuccessResponse(patched, "Libro parcheado correctamente");
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        service.delete(id);
        ApiResponse<String> body = ApiResponseHelper.createSuccessResponse(null, "Libro eliminado correctamente");
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDto>> findById(@PathVariable UUID id) {
        BookDto dto = service.findById(id);
        ApiResponse<BookDto> body = ApiResponseHelper.createSuccessResponse(dto);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookDto>>> search(@RequestParam Optional<String> title,
                                                             @RequestParam Optional<Long> authorId) {
        List<BookDto> results = service.search(title, authorId);
        ApiResponse<List<BookDto>> body = ApiResponseHelper.createSuccessResponse(results);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BookDto>>> findAll(Pageable pageable) {
        Page<BookDto> page = service.findAll(pageable);
        PageResponse<BookDto> pageResponse = ApiResponseHelper.toPageResponse(page);
        ApiResponse<PageResponse<BookDto>> body = ApiResponseHelper.createSuccessResponse(pageResponse);
        return ResponseEntity.ok(body);
    }


    @PostMapping("/search-elastic")
    public ResponseEntity<ApiResponse<PageResponse<BookDto>>> searchInElasticsearch(
            @RequestBody BookSearchRequest req, Pageable pageable) {

        List<BookDocument> documents = searchService.search(req);

        List<BookDto> dtos = documents.stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), dtos.size());
        if (start > dtos.size()) start = dtos.size();

        Page<BookDto> page = new PageImpl<>(dtos.subList(start, end), pageable, dtos.size());

        PageResponse<BookDto> pageResponse = ApiResponseHelper.toPageResponse(page);
        ApiResponse<PageResponse<BookDto>> body = ApiResponseHelper.createSuccessResponse(pageResponse);

        return ResponseEntity.ok(body);
    }

}