package com.finchproject.estagio.service.strategy;

import com.finchproject.estagio.entities.ProductModel;
import com.finchproject.estagio.entities.SearchHistoricModel;
import com.finchproject.estagio.entities.dto.ProductDto;
import com.finchproject.estagio.service.ProductService;
import com.finchproject.estagio.service.SearchHistoricService;
import com.finchproject.estagio.service.exceptions.ProductExceptions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("MERCADOLIVRE")
public class BrowseMercadoLivreService implements BrowseService {
    public static final String NAV_SEARCH_INPUT = "nav-search-input";
    public static final String NAV_SEARCH_BTN = "nav-search-btn";
    public static final String UI_SEARCH_ITEM_TITLE = "shops__item-title";
    public static final String PRICE_TAG_FRACTION = "price-tag-fraction";
    public static final String REGEX = "\\.";
    public static final String PRICE_TAG_CENTS = "price-tag-cents";
    public static final String DOT = ".";
    public static final String EMPTY = "";
    public static final String MAX_PRICE = "99999999";
    public static final String ANDES_CARD_DEFAULT = ".andes-card";
    private final WebDriver webDriver;

    private final ProductService productService;

    private final SearchHistoricService searchHistoricService;

    private final Logger logger = LoggerFactory.getLogger(BrowseMercadoLivreService.class);

    @Value("${marketplace.mercadolivre.url}")
    private String url;

    public BrowseMercadoLivreService(WebDriver webDriver, ProductService productService, SearchHistoricService searchHistoricService) {
        this.webDriver = webDriver;
        this.productService = productService;
        this.searchHistoricService = searchHistoricService;
    }

    @Override
    public List<ProductDto> browseProductByName(String productName) throws InterruptedException {
        logger.info("m=browseProductByName stage=init productName={}", productName);

        var history = searchHistoricService.findByProductSearchedAndExpirationDateGreaterThanEqualService(productName, LocalDate.now());

        if(history.isPresent()) {
            var historic = history.get()
                    .getProducts()
                    .stream()
                    .map(ProductDto::new)
                    .collect(Collectors.toList());
            logger.info("m=browseProductByName stage=historicFound historic={}", historic);
            return historic;
        }

        webDriver.get(url);

        search(productName);

        Thread.sleep(3000);

        List<WebElement> items = webDriver.findElements(By.cssSelector(ANDES_CARD_DEFAULT));
        List<ProductModel> productsFound = new ArrayList<>();

        Thread.sleep(3000);

        productsFound = items.stream()
                .map(this::getProductModel)
                .filter(item -> item.getTitle().toLowerCase().contains(productName.toLowerCase()))
                .limit(3)
                .sorted(Comparator.comparing(ProductModel::getPrice))
                .toList();

        productService.saveAll(productsFound);
        searchHistoricService.save(new SearchHistoricModel(productName, productsFound));

        if (isEmpty(productsFound)) {
            logger.error("m=browseProductByName stage=error productName={}", productName);
            throw new ProductExceptions("Error: The product " + productName + " was not found");
        }

        return productsFound.stream().map(getProductModelFromProductDto()).toList();
    }
    private static Function<ProductModel, ProductDto> getProductModelFromProductDto() {
        return obj -> new ProductDto(obj);
    }

    private static boolean isEmpty(List<ProductModel> productsFound) {
        return productsFound.isEmpty();
    }

    private ProductModel getProductModel(WebElement item) {

        String title = EMPTY;
        String price = MAX_PRICE;

        try{
            title = item.findElement(By.className(UI_SEARCH_ITEM_TITLE)).getText();
            price = handlePrice(item);
        } catch (RuntimeException ignore){}

        return new ProductModel()
                .setTitle(title)
                .setPrice(new BigDecimal(price));
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
        logger.info("m=search stage=init productName={}", productName);
        WebElement searchBox = webDriver.findElement(By.className(NAV_SEARCH_INPUT));
        searchBox.sendKeys(productName);
        WebElement searchButton = webDriver.findElement(By.className(NAV_SEARCH_BTN));
        searchButton.click();
        logger.info("m=search stage=finish productName={}", productName);
    }
}
