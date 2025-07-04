package com.unir.missiact1.msbookscatalogue.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id", length = 36, updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(nullable = false, unique = true)
    private String isbn;

    private Integer rating;
    private Boolean visible;

    @Column(length = 2000)
    private String summary;

    @Column(nullable = false)
    private Integer stock = 0;

    private BigDecimal price;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }

    // solo se muestran los nuevos métodos
    public void decreaseStock(int qty) {
        if (this.stock < qty) { throw new IllegalStateException("Sin stock"); }
        this.stock -= qty;
    }
    public void increaseStock(int qty) { this.stock += qty; }

}