package ru.mirea.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@DiscriminatorValue("plumbings")
public class WashingMachine extends Product {
    private String producer;
    private Integer tank_volume;
}
