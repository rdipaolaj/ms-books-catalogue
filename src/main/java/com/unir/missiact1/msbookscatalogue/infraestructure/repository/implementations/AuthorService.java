package com.unir.missiact1.msbookscatalogue.infraestructure.repository.implementations;

import com.unir.missiact1.msbookscatalogue.application.dtos.AuthorCreateRequest;
import com.unir.missiact1.msbookscatalogue.application.dtos.AuthorDto;
import com.unir.missiact1.msbookscatalogue.application.request.mapster.AuthorMapper;
import com.unir.missiact1.msbookscatalogue.commons.exceptions.CustomException;
import com.unir.missiact1.msbookscatalogue.commons.enums.ApiErrorCode;
import com.unir.missiact1.msbookscatalogue.domain.Author;
import com.unir.missiact1.msbookscatalogue.domain.interfaces.IAuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final IAuthorRepository repo;

    public AuthorService(IAuthorRepository repo) {
        this.repo = repo;
    }

    public AuthorDto create(AuthorCreateRequest req) {
        Author a = new Author();
        a.setName(req.getName());
        return AuthorMapper.toDto(repo.save(a));
    }

    public AuthorDto update(Long id, AuthorDto dto) {
        Author existing = repo.findById(id)
            .orElseThrow(() -> new CustomException("Autor no encontrado", ApiErrorCode.NotFound));
        existing.setName(dto.getName());
        Author updated = repo.save(existing);
        return AuthorMapper.toDto(updated);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new CustomException("Autor no encontrado", ApiErrorCode.NotFound);
        repo.deleteById(id);
    }

    public AuthorDto findById(Long id) {
        return repo.findById(id)
            .map(AuthorMapper::toDto)
            .orElseThrow(() -> new CustomException("Autor no encontrado", ApiErrorCode.NotFound));
    }

    public List<AuthorDto> findAll() {
        return repo.findAll().stream()
            .map(AuthorMapper::toDto)
            .collect(Collectors.toList());
    }
}