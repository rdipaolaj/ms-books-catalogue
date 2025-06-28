package com.unir.missiact1.msbookscatalogue.application.request.mapster;

import com.unir.missiact1.msbookscatalogue.application.dtos.AuthorDto;
import com.unir.missiact1.msbookscatalogue.domain.Author;

public class AuthorMapper {
    public static AuthorDto toDto(Author a) {
        if (a == null) return null;
        AuthorDto d = new AuthorDto();
        d.setId(a.getId());
        d.setName(a.getName());
        return d;
    }

    public static Author toEntity(AuthorDto d) {
        if (d == null) return null;
        Author a = new Author();
        a.setId(d.getId());
        a.setName(d.getName());
        return a;
    }
}