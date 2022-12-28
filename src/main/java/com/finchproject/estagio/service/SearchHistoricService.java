package com.finchproject.estagio.service;

import com.finchproject.estagio.entities.SearchHistoricModel;
import com.finchproject.estagio.repository.SearchHistoricRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SearchHistoricService {
    private final SearchHistoricRepository searchHistoricRepository;

    public SearchHistoricService(SearchHistoricRepository searchHistoricRepository) {
        this.searchHistoricRepository = searchHistoricRepository;
    }

    public SearchHistoricModel save(SearchHistoricModel products) {
        return searchHistoricRepository.save(products);
    }

    public Optional<SearchHistoricModel> findByProductSearchedAndExpirationDateGreaterThanEqualService(String name, LocalDate localDate){
        return searchHistoricRepository.findByProductSearchedAndExpirationDateGreaterThanEqual(name, localDate);
    }

    public List<SearchHistoricModel> findHistory(){
        return searchHistoricRepository.findAll();
    }


}
