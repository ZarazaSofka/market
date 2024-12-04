package ru.mirea.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.market.entity.Product;
import ru.mirea.market.service.ProductService;

@RestController
@RequestMapping("/shop")
@CrossOrigin("*")
public class ShopController {
    @Autowired
    ProductService productService;

    @GetMapping
    Page<? extends Product> getAll(@RequestParam(defaultValue = "") String type,
                                   @RequestParam(defaultValue = "id") SortType sortBy,
                         @RequestParam(defaultValue = "0") int min_price,
                         @RequestParam(defaultValue = "9999999") int max_price,
                         Pageable page) {
        Pageable sortedPage = PageRequest.of(0, page.getPageSize(), sortBy.getSort());

        return productService.getAll(type, min_price, max_price, sortedPage);
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> getOne(@PathVariable Long id) {
        Product p = productService.getById(id);
        if (p == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }
}
