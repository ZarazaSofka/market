package ru.mirea.market.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.market.entity.Order;
import ru.mirea.market.entity.OrderItem;
import ru.mirea.market.entity.Product;
import ru.mirea.market.repository.OrderItemRepository;
import ru.mirea.market.repository.OrderRepository;
import ru.mirea.market.repository.ProductRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class BasketService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;

    public List<OrderItem> getBasketItems(Long id) {
        return getBasket(id).getOrderItems();
    }

    private Order getBasket(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            orders = List.of(newOrder(userId));
        }
        return orders.get(orders.size() - 1);
    }

    private Order newOrder(Long userId) {
        Order order = new Order();
        order.setUserId(userId);
        return orderRepository.save(order);
    }

    public boolean addItem(Long userid, Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        Order order = getBasket(userid);
        if (product == null || order == null)
            return false;

        OrderItem item;
        if (order.getOrderItems() == null) {
            order.setOrderItems(List.of());
        }
        item = order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getProduct().getId() == productId)
                .findFirst().orElse(null);
        if (item == null) {
            item = new OrderItem();
            item.setOrder(order);
            item.setQuantity(0);
            item.setProduct(product);
        }
        if (product.getQuantity() < item.getQuantity() + 1)
            return false;

        item.addItem();
        orderItemRepository.save(item);
        return true;
    }

    public boolean reduceItem(Long userId, Long itemId) {
        OrderItem item = orderItemRepository.findById(itemId).orElse(null);
        if (item == null)
            return false;
        if (item.reduceQuantity())
            orderItemRepository.save(item);
        else {
            orderItemRepository.deleteById(itemId);
        }
        return true;
    }

    public boolean deleteItem(Long userId, Long itemId) {
        OrderItem item = orderItemRepository.findById(itemId).orElse(null);
        if (item == null)
            return false;
        orderItemRepository.delete(item);
        return true;
    }

    public boolean makeOrder(Long userId) {
        Order order = getBasket(userId);
        if (order == null)
            return false;


        if (order.getOrderItems().stream().anyMatch(item -> item.getProduct().getQuantity() < item.getQuantity()))
            return false;
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.reduceQuantity(orderItem.getQuantity());
            productRepository.save(product);
        }
        newOrder(userId);
        return true;
    }
}
