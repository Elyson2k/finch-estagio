package com.finchproject.estagio.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="tb_search_history")
public class SearchHistoricModel {
    @Id
    @GeneratedValue
    private UUID id;
    private String productSearched;
    private LocalDateTime createdDate;
    private LocalDate expirationDate;

    @OneToMany
    private List<ProductModel> products = new ArrayList<>();

    @PrePersist
    private void startCreationDate() {
        createdDate = LocalDateTime.now();
        expirationDate = LocalDate.now().plusDays(7);
    }

    public SearchHistoricModel() {
    }

    public SearchHistoricModel(String productSearched, List<ProductModel> products) {
        this.productSearched = productSearched;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public SearchHistoricModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getProductSearched() {
        return productSearched;
    }

    public SearchHistoricModel setProductSearched(String productSearched) {
        this.productSearched = productSearched;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public SearchHistoricModel setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public SearchHistoricModel setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "SearchHistoryModel{" +
                "id=" + id +
                ", key='" + productSearched + '\'' +
                ", createdDate=" + createdDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
