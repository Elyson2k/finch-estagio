package com.finchproject.estagio.service.strategy;

import com.finchproject.estagio.entities.dto.ProductDto;

import java.util.List;

public interface BrowseService {
    List<ProductDto> browseProductByName(String name) throws InterruptedException;
}
