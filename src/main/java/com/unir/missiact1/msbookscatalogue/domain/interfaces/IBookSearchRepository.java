package com.unir.missiact1.msbookscatalogue.domain.interfaces;

import com.unir.missiact1.msbookscatalogue.domain.BookDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface IBookSearchRepository extends ElasticsearchRepository<BookDocument, String> {
    List<BookDocument> findByTitleContainingIgnoreCase(String title);
    List<BookDocument> findByAuthorNameContainingIgnoreCase(String authorName);
    List<BookDocument> findByIsbn(String isbn);
    List<BookDocument> findByCategoryName(String categoryName);
    List<BookDocument> findByRating(Integer rating);
    List<BookDocument> findByVisible(Boolean visible);
}
