package com.unir.missiact1.msbookscatalogue.infraestructure.repository.implementations;

import com.unir.missiact1.msbookscatalogue.application.dtos.CategoryCreateRequest;
import com.unir.missiact1.msbookscatalogue.application.dtos.CategoryDto;
import com.unir.missiact1.msbookscatalogue.application.request.mapster.CategoryMapper;
import com.unir.missiact1.msbookscatalogue.commons.exceptions.CustomException;
import com.unir.missiact1.msbookscatalogue.commons.enums.ApiErrorCode;
import com.unir.missiact1.msbookscatalogue.domain.Category;
import com.unir.missiact1.msbookscatalogue.domain.interfaces.ICategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final ICategoryRepository repo;

    public CategoryService(ICategoryRepository repo) {
        this.repo = repo;
    }

    public CategoryDto create(CategoryCreateRequest req) {
        Category c = new Category();
        c.setName(req.getName());
        return CategoryMapper.toDto(repo.save(c));
    }

    public CategoryDto update(Long id, CategoryDto dto) {
        Category existing = repo.findById(id)
            .orElseThrow(() -> new CustomException("Categoría no encontrada", ApiErrorCode.NotFound));
        existing.setName(dto.getName());
        Category updated = repo.save(existing);
        return CategoryMapper.toDto(updated);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new CustomException("Categoría no encontrada", ApiErrorCode.NotFound);
        repo.deleteById(id);
    }

    public CategoryDto findById(Long id) {
        return repo.findById(id)
            .map(CategoryMapper::toDto)
            .orElseThrow(() -> new CustomException("Categoría no encontrada", ApiErrorCode.NotFound));
    }

    public List<CategoryDto> findAll() {
        return repo.findAll().stream()
            .map(CategoryMapper::toDto)
            .collect(Collectors.toList());
    }
}