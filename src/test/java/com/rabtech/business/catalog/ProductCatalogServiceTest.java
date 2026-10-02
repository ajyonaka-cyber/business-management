package com.rabtech.business.catalog;

import com.rabtech.business.model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductCatalogServiceTest {

    @Test
    void shouldFilterProductsByMaximumPrice() {
        Product laptop = new Product("P001", "Laptop", 55000);
        Product mouse = new Product("P002", "Mouse", 1000);
        Product keyboard = new Product("P003", "Keyboard", 2000);

        List<Product> products = List.of(laptop, mouse, keyboard);

        ProductCatalogService service = new ProductCatalogService();

        List<Product> result =
                service.filterByMaximumPrice(products, 2000);

        assertEquals(2, result.size());
        assertEquals(mouse, result.get(0));
        assertEquals(keyboard, result.get(1));
    }

    @Test
    void shouldSortProductsByPriceAscending() {
        Product laptop = new Product("P001", "Laptop", 55000);
        Product mouse = new Product("P002", "Mouse", 1000);
        Product keyboard = new Product("P003", "Keyboard", 2000);

        List<Product> products = List.of(laptop, mouse, keyboard);

        ProductCatalogService service = new ProductCatalogService();

        List<Product> result =
                service.sortByPriceAscending(products);

        assertEquals(mouse, result.get(0));
        assertEquals(keyboard, result.get(1));
        assertEquals(laptop, result.get(2));
    }
}