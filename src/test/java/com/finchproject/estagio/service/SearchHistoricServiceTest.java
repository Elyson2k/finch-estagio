package com.finchproject.estagio.service;

import com.finchproject.estagio.entities.ProductModel;
import com.finchproject.estagio.entities.SearchHistoricModel;
import com.finchproject.estagio.repository.SearchHistoricRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SearchHistoricServiceTest {


    public static final String TESTE = "Teste";
    public static final BigDecimal BIG_DECIMAL = new BigDecimal("123");
    public static final String TEST_SEARCHED = "TestSearched";
    @InjectMocks
    private SearchHistoricService searchHistoricService;
    @Mock
    private SearchHistoricRepository searchHistoricRepository;
    private SearchHistoricModel searchHistoricModel;
    private Optional<SearchHistoricModel> optionalSearchHistoricModel;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    LocalDate dt1 = LocalDate.parse("25/02/2003", formatter);

    @Test
    @DisplayName("Salvando uma pesquisa")
    void save() {
        SearchHistoricModel searchHistoricModelNEW = searchHistoricService.save(searchHistoricModel);
        assertNotNull(searchHistoricModelNEW);
        assertEquals("TestSearched", searchHistoricModelNEW.getProductSearched());
        assertEquals("Teste", searchHistoricModelNEW.getProducts().get(0).getTitle());
        assertEquals(new BigDecimal("123"), searchHistoricModelNEW.getProducts().get(0).getPrice());
    }

    @Test
    @DisplayName("Encontrar por produto pesquisado e data de validade maior que o serviço igual")
    void findByProductSearchedAndExpirationDateGreaterThanEqualService() {
        Optional<SearchHistoricModel> optionalSearchHistoricModel1 = searchHistoricService.findByProductSearchedAndExpirationDateGreaterThanEqualService(TESTE, dt1);
        assertNotNull(optionalSearchHistoricModel1);
        assertNotEquals(TESTE,optionalSearchHistoricModel1.get().getProductSearched());
        assertEquals(TEST_SEARCHED, optionalSearchHistoricModel1.get().getProductSearched());
        assertEquals(TESTE, optionalSearchHistoricModel1.get().getProducts().get(0).getTitle());
        assertEquals(BIG_DECIMAL, optionalSearchHistoricModel1.get().getProducts().get(0).getPrice());
    }

    @Test
    @DisplayName("Buscando todas pesquisas")
    void findHistory() {
        List<SearchHistoricModel> searchHistoricModels = searchHistoricService.findHistory();
        assertNotNull(searchHistoricModels);
    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
        when(searchHistoricRepository.save(searchHistoricModel)).thenReturn(searchHistoricModel);
        when(searchHistoricRepository.findByProductSearchedAndExpirationDateGreaterThanEqual(TESTE, dt1)).thenReturn(optionalSearchHistoricModel);
        when(searchHistoricRepository.findAll()).thenReturn(List.of(searchHistoricModel));

    }

    private void startUser() {
        List<ProductModel> products = new ArrayList<>();
        searchHistoricModel = new SearchHistoricModel(TEST_SEARCHED, products);
        products.add(new ProductModel(null, TESTE, BIG_DECIMAL));
        optionalSearchHistoricModel = Optional.of(searchHistoricModel);
    }

}