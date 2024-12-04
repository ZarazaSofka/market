package ru.mirea.auth.entity;

public class LoginAlreadyDefinedException extends RuntimeException{
    public LoginAlreadyDefinedException() {
        super("Login already defined!");
    }
}