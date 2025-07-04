package com.unir.missiact1.msbookscatalogue.domain.interfaces;

import com.unir.missiact1.msbookscatalogue.domain.Book;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface IBookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

	List<Book> findByTitleIgnoreCaseContaining(String title);

    List<Book> findByAuthor_NameIgnoreCaseContaining(String authorName);

    List<Book> findByCategory_NameIgnoreCase(String categoryName);

    List<Book> findByIsbn(String isbn);

    List<Book> findByPublicationDate(LocalDate publicationDate);

    List<Book> findByRating(Integer rating);

    List<Book> findByVisible(Boolean visible);

    @Modifying
    @Query("""
        update Book b
           set b.stock = b.stock - :qty
         where b.id = :id
           and b.stock >= :qty
        """)
    int reserveStock(@Param("id") UUID id, @Param("qty") int qty);

    @Modifying
    @Query("update Book b set b.stock = b.stock + :qty where b.id = :id")
    void releaseStock(@Param("id") UUID id, @Param("qty") int qty);
}