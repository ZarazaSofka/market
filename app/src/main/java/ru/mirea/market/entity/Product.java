package ru.mirea.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public class Product {
    @Id
    @SequenceGenerator(name = "products_seq", sequenceName = "products_seq", allocationSize = 1)
    @GeneratedValue(generator = "products_seq", strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(insertable=false, updatable=false)
    private String type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity = 0;

    private String picture_url;

    public boolean reduceQuantity(int diff) {
        if (this.quantity < diff)
            return false;
        this.quantity -= diff;
        return true;
    }
}