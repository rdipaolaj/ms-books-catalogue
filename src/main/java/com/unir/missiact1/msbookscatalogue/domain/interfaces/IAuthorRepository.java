package com.unir.missiact1.msbookscatalogue.domain.interfaces;

import com.unir.missiact1.msbookscatalogue.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorRepository extends JpaRepository<Author, Long> {
}