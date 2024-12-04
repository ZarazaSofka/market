package ru.mirea.market.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
    @GeneratedValue(generator = "orders_seq", strategy = GenerationType.SEQUENCE)
    private long id;
    private long userId;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    List<OrderItem> orderItems;
}
