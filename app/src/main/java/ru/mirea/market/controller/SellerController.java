package ru.mirea.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mirea.market.entity.*;
import ru.mirea.market.service.ProductService;

import java.security.Principal;

@RestController
@RequestMapping(value = "/stock")
@CrossOrigin("*")
public class SellerController {
    @Autowired
    ProductService productService;

    @PostMapping("/books")
    ResponseEntity<Book> save(@RequestBody Book product) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setSellerId(user.getId());
        Book p = productService.add(product);
        return ResponseEntity.ok(p);
    }

    @PostMapping("/electronics")
    ResponseEntity<Telephone> save(@RequestBody Telephone product) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setSellerId(user.getId());
        Telephone p = productService.add(product);
        return ResponseEntity.ok(p);
    }

    @PostMapping("/plumbings")
    ResponseEntity<WashingMachine> save(@RequestBody WashingMachine product) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setSellerId(user.getId());
        WashingMachine p = productService.add(product);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Product> delete(@PathVariable Long id) {
        if (productService.delete(id))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/books/{id}")
    ResponseEntity<Book> change(@PathVariable Long id, @RequestBody Book product) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setSellerId(user.getId());
        Book p = productService.change(id, product);
        if (p == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    @PutMapping("/electronics/{id}")
    ResponseEntity<Telephone> change(@PathVariable Long id, @RequestBody Telephone product) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setSellerId(user.getId());
        Telephone p = productService.change(id, product);
        if (p == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    @PutMapping("/plumbings/{id}")
    ResponseEntity<WashingMachine> change(@PathVariable Long id, @RequestBody WashingMachine product) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setSellerId(user.getId());
        WashingMachine p = productService.change(id, product);
        if (p == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }
}
