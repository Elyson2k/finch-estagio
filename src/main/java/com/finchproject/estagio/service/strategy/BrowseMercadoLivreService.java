package com.finchproject.estagio.service.strategy;

import com.finchproject.estagio.entities.ProductModel;
import com.finchproject.estagio.entities.SearchHistoricModel;
import com.finchproject.estagio.entities.dto.ProductDto;
import com.finchproject.estagio.repository.SearchHistoricRepository;
import com.finchproject.estagio.service.ProductService;
import com.finchproject.estagio.service.SearchHistoricService;
import com.finchproject.estagio.service.exceptions.ProductExceptions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("MERCADOLIVRE")
public class BrowseMercadoLivreService implements BrowseService {
    public static final String NAV_SEARCH_INPUT = "nav-search-input";
    public static final String NAV_SEARCH_BTN = "nav-search-btn";
    public static final String ANDES_CARD = ".andes-card";
    public static final String UI_SEARCH_ITEM_TITLE = "ui-search-item__title";
    public static final String PRICE_TAG_FRACTION = "price-tag-fraction";
    public static final String REGEX = "\\.";
    public static final String PRICE_TAG_CENTS = "price-tag-cents";
    public static final String DOT = ".";
    public static final String EMPTY = "";
    private final WebDriver webDriver;

    private final ProductService productService;

    private final SearchHistoricRepository searchHistoricRepository;

    private final SearchHistoricService searchHistoricService;

    @Value("${marketplace.mercadolivre.url}")
    private String url;

    public BrowseMercadoLivreService(WebDriver webDriver, ProductService productService, SearchHistoricRepository searchHistoricRepository, SearchHistoricService searchHistoricService) {
        this.webDriver = webDriver;
        this.productService = productService;
        this.searchHistoricRepository = searchHistoricRepository;
        this.searchHistoricService = searchHistoricService;
    }

    @Override
    public List<ProductDto> browseProductByName(String productName) {
        var history = searchHistoricService.findByProductSearchedAndExpirationDateGreaterThanEqualService(productName, LocalDate.now());

        if(history.isPresent()) {
            return history.get()
                    .getProducts()
                    .stream()
                    .map(ProductDto::new)
                    .collect(Collectors.toList());
        }

        webDriver.get(url);

        search(productName);
        List<WebElement> items = webDriver.findElements(By.cssSelector(ANDES_CARD));
        var productsFound = items.stream().map((item) -> {
                    String title = item.findElement(By.className(UI_SEARCH_ITEM_TITLE)).getText();
                    String price = handlePrice(item);
                    return new ProductModel()
                            .setTitle(title)
                            .setPrice(new BigDecimal(price));
                }).filter(item -> item.getTitle().toLowerCase().contains(productName.toLowerCase()))
                .sorted((Comparator.comparing(ProductModel::getPrice)))
                .limit(3)
                .toList();
        productService.saveAll(productsFound);
        searchHistoricService.save(new SearchHistoricModel(productName, productsFound));

        if(items.isEmpty()){
            throw new ProductExceptions("Error: The product sought was not found");
        }

        return productsFound.stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());

    }

    private String handlePrice(WebElement item) {
        var price = item.findElement(By.className(PRICE_TAG_FRACTION)).getText();
        if(price.length() >= 4) {
            price = extractPrice(price);
        }
        addCentsToPrice(item, price);
        return price;
    }

    private void addCentsToPrice(WebElement item, String price) {
        try {
            price += DOT + item.findElement(By.className(PRICE_TAG_CENTS)).getText();
        } catch (NoSuchElementException ignored) {
        }
    }

    private static String extractPrice(String price) {
        return String.join(EMPTY, price.split(REGEX));
    }

    private void search(String productName) {
        WebElement searchBox = webDriver.findElement(By.className(NAV_SEARCH_INPUT));
        searchBox.sendKeys(productName);
        WebElement searchButton = webDriver.findElement(By.className(NAV_SEARCH_BTN));
        searchButton.click();
    }
}
