package com.unir.missiact1.msbookscatalogue.application.dtos;

import lombok.Data;

import java.time.LocalDate;
@Data
public class BookSearchRequest {
    private String title;
    private String authorName;
    private String categoryName;
    private String isbn;
    private Integer rating;
    private Boolean visible;
    private LocalDate publicationDate;
}
