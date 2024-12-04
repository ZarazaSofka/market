package ru.mirea.auth.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.mirea.auth.entity.User;

public interface UserRepository extends ListCrudRepository<User, Long> {
}