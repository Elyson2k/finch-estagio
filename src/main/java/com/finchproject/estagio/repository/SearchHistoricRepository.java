package com.finchproject.estagio.repository;

import com.finchproject.estagio.entities.SearchHistoricModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SearchHistoricRepository extends JpaRepository<SearchHistoricModel, UUID> {
    Optional<SearchHistoricModel> findByProductSearchedAndExpirationDateGreaterThanEqual(String name, LocalDate now);
}
