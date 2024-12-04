package ru.mirea.market.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.market.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByTypeAndPriceBetween(String type, int min, int max, Pageable pageable);
    Page<Product> findAllByPriceBetween(int min, int max, Pageable pageable);
}
