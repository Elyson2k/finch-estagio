package com.finchproject.estagio.controller;

import com.finchproject.estagio.entities.ProductModel;
import com.finchproject.estagio.entities.SearchHistoricModel;
import com.finchproject.estagio.entities.dto.ProductDto;
import com.finchproject.estagio.entities.dto.SearchHistoryDto;
import com.finchproject.estagio.enums.MarketplaceOption;
import com.finchproject.estagio.service.SearchHistoricService;
import com.finchproject.estagio.service.strategy.BrowseService;
import com.finchproject.estagio.service.strategy.BrowseServiceStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class BrowseControllerTest {

    public static final String TESTE = "Teste";
    public static final BigDecimal BIG_DECIMAL = new BigDecimal("123");
    public static final String WEBCAM = "webcam";

    @InjectMocks
    private BrowseController browseController;
    @Mock
    private BrowseServiceStrategy browseServiceStrategy;

    @Mock
    private SearchHistoricService searchHistoricService;

    @Mock
    BrowseService browseService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    LocalDate dt1 = LocalDate.parse("25/02/2003", formatter);
    LocalDateTime agora = LocalDateTime.now();
    List<ProductDto> productDtoList = new ArrayList<>();
    ProductDto productDto = new ProductDto("Test", new BigDecimal(123));
    List<SearchHistoryDto> listDtoSearched = new ArrayList<>();
    List<ProductModel> products = new ArrayList<>();
    SearchHistoryDto searchHistoryDto = new SearchHistoryDto(null, TESTE, agora, dt1, products);
    SearchHistoricModel searchHistoricModel = new SearchHistoricModel(TESTE, products);

    @Test
    @DisplayName("Buscar produtos por nome + site")
    void findProducts() throws InterruptedException {

        ResponseEntity<List<ProductDto>> listDto = browseController.findProducts(WEBCAM, MarketplaceOption.MERCADOLIVRE);
        listDto.getBody().add(productDto);

        assertNotNull(listDto);
        assertNotNull(listDto.getBody());
        assertEquals(ResponseEntity.class, listDto.getClass());
        assertEquals("Test" , listDto.getBody().get(0).getTitle());
        assertEquals(new BigDecimal("123"), listDto.getBody().get(0).getPrice());
    }

    @Test
    void findHistoryProductsSearchered() {

        ResponseEntity<List<SearchHistoryDto>> listDto = browseController.findHistoryProductsSearchered();
        assertNotNull(listDto);
        assertNotNull(listDto.getBody());
        assertEquals(ResponseEntity.class, listDto.getClass());
        assertEquals("Teste" , listDto.getBody().get(0).getProductSearched());
        assertEquals(new BigDecimal("123"), listDto.getBody().get(0).getProducts().get(0).getPrice());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
        when(browseServiceStrategy.getService(anyString())).thenReturn(browseService);
        when(searchHistoricService.findHistory()).thenReturn(List.of(searchHistoricModel));
    }

    private void startUser() {
        listDtoSearched.add(searchHistoryDto);
        products.add(new ProductModel(null, TESTE, BIG_DECIMAL));
        String productSearched = WEBCAM;
        productDtoList.add(productDto);
    }
}