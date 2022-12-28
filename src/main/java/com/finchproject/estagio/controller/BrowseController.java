package com.finchproject.estagio.controller;

import com.finchproject.estagio.entities.dto.ProductDto;
import com.finchproject.estagio.entities.dto.SearchHistoryDto;
import com.finchproject.estagio.enums.MarketplaceOption;
import com.finchproject.estagio.service.SearchHistoricService;
import com.finchproject.estagio.service.strategy.BrowseServiceStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@RestController
public class BrowseController implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(BrowseController.class);
    private final BrowseServiceStrategy browseServiceStrategy;

    private final SearchHistoricService searchHistoricService;

    public BrowseController(BrowseServiceStrategy browseServiceStrategy, SearchHistoricService searchHistoricService) {
        this.browseServiceStrategy = browseServiceStrategy;
        this.searchHistoricService = searchHistoricService;
    }


    @GetMapping("/produtos/{productName}")
    public ResponseEntity<List<ProductDto>> findProducts(@PathVariable String productName, @RequestParam MarketplaceOption marketplace) {
        List<ProductDto> listProducts = browseServiceStrategy.getService(marketplace.name()).browseProductByName(productName);
        return ResponseEntity.ok(listProducts);
    }

    @GetMapping("/history-search")
    public ResponseEntity<List<SearchHistoryDto>> findHistoryProductsSearchered(){
        List<SearchHistoryDto> listDto = searchHistoricService.findHistory().stream().map(item -> new SearchHistoryDto(item)).toList();
        return ResponseEntity.ok(listDto);
    }
}
