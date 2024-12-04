package ru.mirea.market.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.mirea.market.entity.OrderItem;
import ru.mirea.market.entity.User;
import ru.mirea.market.service.BasketService;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/my")
public class BasketController {
    private BasketService basketService;
    @GetMapping("/{userId}")
    ResponseEntity<List<OrderItem>> getBasketProducts(@PathVariable Long userId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(user.getId(), userId) && !Objects.equals(user.getRole(), "ROLE_ADMIN"))
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
        return ResponseEntity.ok(basketService.getBasketItems(userId));
    }

    @PostMapping("/{userId}/{productId}")
    ResponseEntity<String> addBasketProduct(@PathVariable Long userId, @PathVariable Long productId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(user.getId(), userId) && !Objects.equals(user.getRole(), "ROLE_ADMIN"))
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();

        if (basketService.addItem(userId, productId))
            return  ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}/{itemId}")
    ResponseEntity<String> reduceBasketProduct(@PathVariable Long userId, @PathVariable Long itemId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(user.getId(), userId) && !Objects.equals(user.getRole(), "ROLE_ADMIN"))
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();

        if (basketService.reduceItem(userId, itemId))
            return  ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}/{itemId}")
    ResponseEntity<String> deleteBasketProduct(@PathVariable Long userId, @PathVariable Long itemId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(user.getId(), userId) && !Objects.equals(user.getRole(), "ROLE_ADMIN"))
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();

        if (basketService.deleteItem(userId, itemId))
            return  ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{userId}")
    ResponseEntity<String> makeOrder(@PathVariable Long userId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(user.getId(), userId) && !Objects.equals(user.getRole(), "ROLE_ADMIN"))
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();

        if (basketService.makeOrder(userId))
            return  ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
