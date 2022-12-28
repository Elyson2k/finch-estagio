package com.finchproject.estagio.service;

import com.finchproject.estagio.entities.ProductModel;
import com.finchproject.estagio.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductModel> saveAll(List<ProductModel> products) {
        return productRepository.saveAll(products);
    }
}
