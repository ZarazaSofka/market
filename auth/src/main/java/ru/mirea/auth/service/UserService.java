package ru.mirea.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.auth.entity.LoginAlreadyDefinedException;
import ru.mirea.auth.entity.User;
import ru.mirea.auth.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setRole("ROLE_ADMIN");
            admin.setLogin("admin");
            admin.setId(1L);
            admin.setName("Admin");
            admin.setEmail("admin@admin.ru");
            admin.setPassword(passwordEncoder.encode("1111"));
            userRepository.save(admin);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findAll().stream().filter(u -> Objects.equals(u.getLogin(), s)).findFirst();
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return user.get();
    }

    @Transactional
    public Long register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setId(userRepository.count() + 1);
        if (userRepository.findAll().stream().anyMatch(u -> Objects.equals(u.getLogin(), user.getLogin()))) {
            throw new LoginAlreadyDefinedException();
        }

        return userRepository.save(user).getId();
    }

    @Transactional
    public User updateToSeller(Long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setRole("ROLE_SELLER");
        return userRepository.save(user);
    }

    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}