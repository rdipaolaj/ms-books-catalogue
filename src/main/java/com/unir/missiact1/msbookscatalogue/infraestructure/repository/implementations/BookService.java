package com.unir.missiact1.msbookscatalogue.infraestructure.repository.implementations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.unir.missiact1.msbookscatalogue.application.dtos.BookCreateRequest;
import com.unir.missiact1.msbookscatalogue.application.dtos.BookDto;
import com.unir.missiact1.msbookscatalogue.application.request.mapster.BookMapper;
import com.unir.missiact1.msbookscatalogue.commons.enums.ApiErrorCode;
import com.unir.missiact1.msbookscatalogue.commons.exceptions.CustomException;
import com.unir.missiact1.msbookscatalogue.domain.Author;
import com.unir.missiact1.msbookscatalogue.domain.Book;
import com.unir.missiact1.msbookscatalogue.domain.Category;
import com.unir.missiact1.msbookscatalogue.domain.interfaces.IAuthorRepository;
import com.unir.missiact1.msbookscatalogue.domain.interfaces.IBookRepository;
import com.unir.missiact1.msbookscatalogue.domain.interfaces.ICategoryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final IBookRepository bookRepo;
    private final IAuthorRepository authorRepo;
    private final ICategoryRepository categoryRepo;

    private final Cloudinary cloudinary;

    public BookService(IBookRepository bookRepo, IAuthorRepository authorRepo, ICategoryRepository categoryRepo, Cloudinary cloudinary) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.categoryRepo = categoryRepo;
        this.cloudinary = cloudinary;
    }

    public BookDto create(BookCreateRequest req, MultipartFile coverImageFile) throws IOException {
        Author author = authorRepo.findById(req.getAuthorId())
            .orElseThrow(() -> new CustomException("Autor no encontrado", ApiErrorCode.NotFound));

        Category category = categoryRepo.findById(req.getCategoryId())
            .orElseThrow(() -> new CustomException("Categoría no encontrada", ApiErrorCode.NotFound));

        Book book = new Book();
        book.setTitle(         req.getTitle());
        book.setPublicationDate(req.getPublicationDate());
        book.setIsbn(          req.getIsbn());
        book.setRating(        req.getRating());
        book.setVisible(       req.getVisible());
        book.setSummary(       req.getSummary());
        book.setPrice(         req.getPrice());
        book.setAuthor(        author);
        book.setCategory(      category);

        if (coverImageFile != null && !coverImageFile.isEmpty()) {
            Map<?,?> uploadResult = cloudinary.uploader()
                    .upload(coverImageFile.getBytes(),
                            ObjectUtils.asMap("folder", "books"));
            book.setCoverImageUrl((String) uploadResult.get("secure_url"));
        }
        Book saved = bookRepo.save(book);
        return BookMapper.toDto(saved);
    }

    public BookDto update(UUID id, BookDto dto) {
        Book existing = bookRepo.findById(id)
            .orElseThrow(() -> new CustomException("Libro no encontrado", ApiErrorCode.NotFound));
        existing.setTitle(dto.getTitle());
        existing.setPublicationDate(dto.getPublicationDate());
        existing.setIsbn(dto.getIsbn());
        existing.setRating(dto.getRating());
        existing.setVisible(dto.getVisible());
        existing.setSummary(dto.getSummary());
        existing.setPrice(dto.getPrice());
        // actualizar author/category si cambia
        if (!existing.getAuthor().getId().equals(dto.getAuthorId())) {
            Author a = authorRepo.findById(dto.getAuthorId())
                .orElseThrow(() -> new CustomException("Autor no encontrado", ApiErrorCode.NotFound));
            existing.setAuthor(a);
        }
        if (!existing.getCategory().getId().equals(dto.getCategoryId())) {
            Category c = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new CustomException("Categoría no encontrada", ApiErrorCode.NotFound));
            existing.setCategory(c);
        }
        Book updated = bookRepo.save(existing);
        return BookMapper.toDto(updated);
    }

    @Transactional
    public BookDto patch(UUID id, Map<String, Object> updates) {
        Book existing = bookRepo.findById(id)
            .orElseThrow(() -> new CustomException("Libro no encontrado", ApiErrorCode.NotFound));
        updates.forEach((k, v) -> {
            switch (k) {
                case "title":
                    existing.setTitle((String) v);
                    break;
                case "publicationDate":
                    existing.setPublicationDate(LocalDate.parse(v.toString()));
                    break;
                case "isbn":
                    existing.setIsbn((String) v);
                    break;
                case "rating":
                    existing.setRating((Integer) v);
                    break;
                case "visible":
                    existing.setVisible((Boolean) v);
                    break;
                case "summary":
                    existing.setSummary((String) v);
                    break;
                case "price":
                    existing.setPrice(new BigDecimal(v.toString()));
                    break;
                case "authorId":
                    Long newAuthorId = Long.parseLong(v.toString());
                    Author a = authorRepo.findById(newAuthorId)
                        .orElseThrow(() -> new CustomException("Autor no encontrado", ApiErrorCode.NotFound));
                    existing.setAuthor(a);
                    break;
                case "categoryId":
                    Long newCatId = Long.parseLong(v.toString());
                    Category c = categoryRepo.findById(newCatId)
                        .orElseThrow(() -> new CustomException("Categoría no encontrada", ApiErrorCode.NotFound));
                    existing.setCategory(c);
                    break;
                default:
                    // campo no manejado
                    break;
            }
        });
        Book saved = bookRepo.save(existing);
        return BookMapper.toDto(saved);
    }

    public void delete(UUID id) {
        if (!bookRepo.existsById(id)) {
            throw new CustomException("Libro no encontrado", ApiErrorCode.NotFound);
        }
        bookRepo.deleteById(id);
    }

    public BookDto findById(UUID id) {
        return bookRepo.findById(id)
            .map(BookMapper::toDto)
            .orElseThrow(() -> new CustomException("Libro no encontrado", ApiErrorCode.NotFound));
    }

    public List<BookDto> search(Optional<String> title, Optional<Long> authorId) {
        Specification<Book> spec = Specification.where(null);
        if (title.isPresent()) {
            spec = spec.and((root, q, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.get().toLowerCase() + "%"));
        }
        if (authorId.isPresent()) {
            spec = spec.and((root, q, cb) ->
                cb.equal(root.get("author").get("id"), authorId.get()));
        }
        return bookRepo.findAll(spec).stream()
            .map(BookMapper::toDto)
            .collect(Collectors.toList());
    }

    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepo.findAll(pageable).map(BookMapper::toDto);
    }

    @Transactional
    public void reserveStock(UUID bookId, int qty) {
        if (bookRepo.reserveStock(bookId, qty) == 0) {
            throw new CustomException("Stock insuficiente", ApiErrorCode.OutOfStock);
        }
    }

    @Transactional
    public void releaseStock(UUID bookId, int qty) {
        bookRepo.releaseStock(bookId, qty);
    }

}