package ru.mirea.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@DiscriminatorValue("books")
public class Book extends Product {
    private String author;
}
