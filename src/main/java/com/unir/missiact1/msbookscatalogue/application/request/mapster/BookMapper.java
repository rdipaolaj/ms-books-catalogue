package com.unir.missiact1.msbookscatalogue.application.request.mapster;

import com.unir.missiact1.msbookscatalogue.application.dtos.BookDto;
import com.unir.missiact1.msbookscatalogue.domain.Book;
import com.unir.missiact1.msbookscatalogue.domain.Author;
import com.unir.missiact1.msbookscatalogue.domain.BookDocument;
import com.unir.missiact1.msbookscatalogue.domain.Category;

import java.util.UUID;

public class BookMapper {
    public static BookDto toDto(Book b) {
        if (b == null) return null;
        BookDto d = new BookDto();
        d.setId(b.getId());
        d.setTitle(b.getTitle());
        d.setCoverImage(b.getCoverImageUrl());
        d.setAuthorId(b.getAuthor().getId());
        d.setAuthor(b.getAuthor().getName());
        d.setCategoryId(b.getCategory().getId());
        d.setCategory(b.getCategory().getName());
        d.setPublicationDate(b.getPublicationDate());
        d.setIsbn(b.getIsbn());
        d.setRating(b.getRating());
        d.setVisible(b.getVisible());
        d.setDescription(b.getSummary());
        d.setPrice(b.getPrice());
        d.setCreatedAt(b.getCreatedAt());
        d.setUpdatedAt(b.getUpdatedAt());
        d.setStock(b.getStock());
        return d;
    }

    public static Book toEntity(BookDto d) {
        if (d == null) return null;
        Book b = new Book();
        b.setId(d.getId());
        b.setTitle(d.getTitle());
        b.setIsbn(d.getIsbn());
        b.setPublicationDate(d.getPublicationDate());
        b.setRating(d.getRating());
        b.setVisible(d.getVisible());
        b.setSummary(d.getDescription());
        b.setPrice(d.getPrice());
        b.setStock(d.getStock());
        Author a = new Author(); a.setId(d.getAuthorId()); b.setAuthor(a);
        Category c = new Category(); c.setId(d.getCategoryId()); b.setCategory(c);
        return b;
    }

    public static BookDto toDto(BookDocument b) {
        if (b == null) return null;
        BookDto d = new BookDto();
        d.setId(b.getId() != null ? UUID.fromString(b.getId()) : null);
        d.setTitle(b.getTitle());
        d.setCoverImage(b.getCoverImage());
        d.setAuthor(b.getAuthorName());
        d.setCategory(b.getCategoryName());
        d.setPublicationDate(b.getPublicationDate());
        d.setIsbn(b.getIsbn());
        d.setRating(b.getRating());
        d.setVisible(b.getVisible());
        d.setDescription(b.getSummary());
        d.setPrice(b.getPrice());
        d.setStock(b.getStock());
        d.setCreatedAt(b.getCreatedAt());
        d.setUpdatedAt(b.getUpdatedAt());
        return d;
    }
}