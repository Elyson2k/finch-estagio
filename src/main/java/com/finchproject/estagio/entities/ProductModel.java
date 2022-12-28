package com.finchproject.estagio.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_product")
public class ProductModel implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private BigDecimal price;
    private LocalDateTime createdDate;

    @PrePersist
    private void startCreationDate() {
        createdDate = LocalDateTime.now();
    }

    public ProductModel(){};

    public ProductModel(UUID id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ProductModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", createdDate=" + createdDate +
                '}';
    }
}
