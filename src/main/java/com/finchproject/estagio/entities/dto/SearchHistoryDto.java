package com.finchproject.estagio.entities.dto;

import com.finchproject.estagio.entities.ProductModel;
import com.finchproject.estagio.entities.SearchHistoricModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SearchHistoryDto {

    private UUID id;
    private String productSearched;
    private LocalDateTime createdDate;
    private LocalDate expirationDate;
    private List<ProductModel> products = new ArrayList<>();

    public SearchHistoryDto(UUID id, String productSearched, LocalDateTime createdDate, LocalDate expirationDate, List<ProductModel> products) {
        this.id = id;
        this.productSearched = productSearched;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
        this.products = products;
    }

    public SearchHistoryDto(SearchHistoricModel item) {
        this.id = item.getId();
        this.productSearched = item.getProductSearched();
        this.createdDate = item.getCreatedDate();
        this.expirationDate = item.getExpirationDate();
        this.products = item.getProducts();
    }

    public String getProductSearched() {
        return productSearched;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

}
