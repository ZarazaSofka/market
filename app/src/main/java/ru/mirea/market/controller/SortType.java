package ru.mirea.market.controller;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum SortType {
    id(Sort.by("id")),
    asc(Sort.by("price").ascending().and(Sort.by("id"))),
    desc(Sort.by("price").descending().and(Sort.by("id")));

    private final Sort sort;

    SortType(Sort sort) {this.sort = sort;}
}
