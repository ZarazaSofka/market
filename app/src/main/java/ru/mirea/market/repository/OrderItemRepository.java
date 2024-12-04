package ru.mirea.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.market.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
