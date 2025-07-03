package com.unir.missiact1.msbookscatalogue.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(indexName = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDocument {
    @Id
    private String id;
    private String title;
    private String coverImage;
    private String authorName;
    private String categoryName;
    private LocalDate publicationDate;
    private String isbn;
    private Integer rating;
    private Boolean visible;
    private String summary;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
