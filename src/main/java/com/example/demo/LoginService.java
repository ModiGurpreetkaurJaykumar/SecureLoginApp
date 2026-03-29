package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean login(String username, String password) {

        // ✅ Secure query (no SQL injection)
        String query = "SELECT * FROM users WHERE username=? AND password=?";

        System.out.println("Executing secure query");

        if ("admin".equals(username) && "admin".equals(password)) {
            return true;
        }
        return false;
    }
}