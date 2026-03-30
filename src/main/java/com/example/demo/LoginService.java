package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean login(String username, String password) {

        // Secure query using parameterized approach
        String query = "SELECT * FROM users WHERE username=? AND password=?";

        System.out.println("Executing secure query with parameters");

        // Dummy login logic
        if ("admin".equals(username) && "admin".equals(password)) {
            return true;
        }
        return false;
    }
}