package com.unir.missiact1.msbookscatalogue.application.request.mapster;

import com.unir.missiact1.msbookscatalogue.application.dtos.CategoryDto;
import com.unir.missiact1.msbookscatalogue.domain.Category;

public class CategoryMapper {
    public static CategoryDto toDto(Category c) {
        if (c == null) return null;
        CategoryDto d = new CategoryDto();
        d.setId(c.getId());
        d.setName(c.getName());
        return d;
    }

    public static Category toEntity(CategoryDto d) {
        if (d == null) return null;
        Category c = new Category();
        c.setId(d.getId());
        c.setName(d.getName());
        return c;
    }
}