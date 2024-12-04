package ru.mirea.market.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.market.entity.Product;
import ru.mirea.market.repository.ProductRepository;

import java.util.Objects;


@Transactional
@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public <T extends Product> T add(T product) {
        return productRepository.save(product);
    }

    public boolean delete(Long id) {
        if (!productRepository.existsById(id))
            return false;
        productRepository.deleteById(id);
        return true;
    }

    public Page<? extends Product> getAll(String type, int min, int max, Pageable pageable) {
        if (!Objects.equals(type, "")) {
            return productRepository.findAllByTypeAndPriceBetween(type, min, max, pageable);
        }
        return productRepository.findAllByPriceBetween(min, max, pageable);
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public <T extends Product> T change(Long id, T product) {
        if (!productRepository.existsById(id))
            return null;
        product.setId(id);
        return productRepository.save(product);
    }
}