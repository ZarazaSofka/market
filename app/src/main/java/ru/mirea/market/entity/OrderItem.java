package ru.mirea.market.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class OrderItem {
    @Id
    @SequenceGenerator(name = "items_seq", sequenceName = "items_seq", allocationSize = 1)
    @GeneratedValue(generator = "items_seq", strategy = GenerationType.SEQUENCE)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Order order;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private int quantity;

    public boolean reduceQuantity() {
        if (this.quantity <= 1)
            return false;
        this.quantity -= 1;
        return true;
    }

    public void addItem() {
        this.quantity += 1;
    }
}
