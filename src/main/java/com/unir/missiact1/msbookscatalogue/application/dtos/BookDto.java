package com.unir.missiact1.msbookscatalogue.application.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private UUID id;

    @NotBlank
    private String title;

    @NotNull
    private Long authorId;

    private String authorName;

    @NotNull
    private Long categoryId;

    private String categoryName;

    @NotNull
    private LocalDate publicationDate;

    @NotBlank
    private String isbn;

    @Min(1) @Max(5)
    private Integer rating;

    @NotNull
    private Boolean visible;

    private String summary;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Integer stock = 0;

    private String coverImageUrl;
}