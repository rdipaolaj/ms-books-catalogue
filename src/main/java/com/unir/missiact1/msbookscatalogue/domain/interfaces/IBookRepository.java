package com.unir.missiact1.msbookscatalogue.domain.interfaces;

import com.unir.missiact1.msbookscatalogue.domain.Book;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

	List<Book> findByTitleIgnoreCaseContaining(String title);

    List<Book> findByAuthor_NameIgnoreCaseContaining(String authorName);

    List<Book> findByCategory_NameIgnoreCase(String categoryName);

    List<Book> findByIsbn(String isbn);

    List<Book> findByPublicationDate(LocalDate publicationDate);

    List<Book> findByRating(Integer rating);

    List<Book> findByVisible(Boolean visible);
}