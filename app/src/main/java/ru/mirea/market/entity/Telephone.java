package ru.mirea.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@DiscriminatorValue("electronics")
public class Telephone extends Product {
    private String producer;
    private Integer battery_capacity;
}
