package com.finchproject.estagio.service;

import com.finchproject.estagio.entities.ProductModel;
import com.finchproject.estagio.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    LocalDateTime agora = LocalDateTime.now();
    BigDecimal bigDecimal = new BigDecimal("123");

    private ProductModel productModel;
    private List<ProductModel> list = new ArrayList<>();

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    ProductServiceTest() throws ParseException {
    }

    @Test
    @DisplayName("Salva todos os produtos.")
    void saveAll() {
        List<ProductModel> list = productService.saveAll(List.of(productModel));
        assertNotNull(list);
        assertEquals("Teste", list.get(0).getTitle());
        assertEquals(new BigDecimal("123"), list.get(0).getPrice());
    }




    //
    // =============================== FUNÇÕES ==========================================
    //




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
        when(productRepository.saveAll(List.of(productModel))).thenReturn(List.of(productModel));

    }

    private void startUser() {
        productModel = new ProductModel(null, "Teste", bigDecimal);
        list.add(productModel);
    }

}