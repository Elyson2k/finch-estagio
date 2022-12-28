//package com.finchproject.estagio.service.strategy;
//
//import com.finchproject.estagio.entities.ProductModel;
//import com.finchproject.estagio.entities.SearchHistoricModel;
//import com.finchproject.estagio.entities.dto.ProductDto;
//import com.finchproject.estagio.repository.SearchHistoricRepository;
//import com.finchproject.estagio.service.ProductService;
//import com.finchproject.estagio.service.SearchHistoricService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.openqa.selenium.WebDriver;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static com.finchproject.estagio.controller.BrowseControllerTest.BIG_DECIMAL;
//import static com.finchproject.estagio.controller.BrowseControllerTest.TESTE;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//public class BrowseProductByNameTest {
//
//    @InjectMocks
//    private WebDriver webDriver;
//
//    @Mock
//    private ProductService productService;
//
//    @Mock
//    private SearchHistoricRepository searchHistoricRepository;
//
//    @Mock
//    private SearchHistoricService searchHistoricService;
//
//    @Mock
//    private BrowseMercadoLivreService browseMercadoLivreService = new BrowseMercadoLivreService(webDriver, productService, searchHistoricRepository, searchHistoricService);
//
//    List<ProductModel> products = Arrays.asList(new ProductModel(null, TESTE, BIG_DECIMAL));
//    SearchHistoricModel searchHistoricModel = new SearchHistoricModel(TESTE, products);
//    private List<ProductDto> productDtoList = new ArrayList<>();
//    private ProductDto productDto = new ProductDto("Test", new BigDecimal(123));
//    private Optional<SearchHistoricModel> optionalSearchHistoricModel = Optional.of(searchHistoricModel);
//
//    @Test
//    void testBrowseProductByName(){
//        when(searchHistoricService.findByProductSearchedAndExpirationDateGreaterThanEqualService(any(), LocalDate.now())).thenReturn(optionalSearchHistoricModel);
//
//        List<ProductDto> listDto = browseMercadoLivreService.browseProductByName("teste");
//        assertEquals("teste", listDto.get(0).getTitle());
//    }
//}
