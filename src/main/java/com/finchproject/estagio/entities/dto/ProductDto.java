package com.finchproject.estagio.entities.dto;

import com.finchproject.estagio.entities.ProductModel;

import java.math.BigDecimal;

public class ProductDto {
    private String title;
    private BigDecimal price;


    public ProductDto(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public ProductDto(ProductModel obj) {
        this.title = obj.getTitle();
        this.price = obj.getPrice();
    }

    public String getTitle() {
        return title;
    }

    public ProductDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "ProdutoDto{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
