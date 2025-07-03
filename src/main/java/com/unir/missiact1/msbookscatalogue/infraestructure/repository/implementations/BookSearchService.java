package com.unir.missiact1.msbookscatalogue.infraestructure.repository.implementations;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Suggestion;
import com.unir.missiact1.msbookscatalogue.application.dtos.BookSearchRequest;
import com.unir.missiact1.msbookscatalogue.domain.BookDocument;
import com.unir.missiact1.msbookscatalogue.domain.interfaces.IBookSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.suggest.response.CompletionSuggestion;
import org.springframework.data.elasticsearch.core.suggest.response.Suggest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookSearchService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final IBookSearchRepository repository;
    private final ElasticsearchClient elasticsearchClient;

    @Autowired
    public BookSearchService(ElasticsearchOperations elasticsearchOperations,
                             IBookSearchRepository repository,
                             ElasticsearchClient elasticsearchClient) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.repository = repository;
        this.elasticsearchClient = elasticsearchClient;
    }

    public List<BookDocument> search(BookSearchRequest req) {
        Criteria criteria = new Criteria();

        if (req.getTitle() != null && !req.getTitle().isBlank()) {
            criteria = criteria.and("title").contains(req.getTitle());
        }
        if (req.getAuthorName() != null && !req.getAuthorName().isBlank()) {
            criteria = criteria.and("authorName").contains(req.getAuthorName());
        }
        if (req.getCategoryName() != null && !req.getCategoryName().isBlank()) {
            criteria = criteria.and("categoryName").contains(req.getCategoryName());
        }
        if (req.getRating() != null) {
            criteria = criteria.and("rating").is(req.getRating());
        }
        if (req.getIsbn() != null && !req.getIsbn().isBlank()) {
            criteria = criteria.and("isbn").is(req.getIsbn());
        }
        if (req.getVisible() != null) {
            criteria = criteria.and("visible").is(req.getVisible());
        }
        if (req.getPublicationDate() != null) {
            criteria = criteria.and("publicationDate").is(req.getPublicationDate());
        }

        CriteriaQuery query = new CriteriaQuery(criteria);

        SearchHits<BookDocument> results = elasticsearchOperations.search(query, BookDocument.class);

        return results.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    /*public List<String> autocompleteTitle(String prefix) throws IOException {
        SearchResponse<BookDocument> response = elasticsearchClient.search(s -> s
                        .index("books")
                        .suggest(sugg -> sugg
                                .completion("title-suggest", c -> c
                                        .field("title_autocomplete")
                                        .prefix(prefix)
                                        .skipDuplicates(true)
                                        .size(10)
                                )
                        ),
                BookDocument.class);

        Suggest suggest = response.suggest();
        if (suggest == null) return List.of();

        List<Suggestion> suggestions = suggest.get("title-suggest");
        if (suggestions == null || suggestions.isEmpty()) return List.of();

        CompletionSuggestion completionSuggestion = (CompletionSuggestion) suggestions.get(0);

        return completionSuggestion.options().stream()
                .map(CompletionSuggestionOption::text)
                .collect(Collectors.toList());
    }*/
}
