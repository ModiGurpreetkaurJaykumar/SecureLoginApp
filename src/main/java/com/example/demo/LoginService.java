package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    // Hardcoded secret (unused variable) — triggers SpotBugs URF_UNREAD_FIELD
    private String dbPassword = "admin123";

    public boolean login(String username, String password) {

        // Vulnerable SQL query (string concatenation) — potential SQL Injection
        String query = "SELECT * FROM users WHERE username='" + username +
                "' AND password='" + password + "'";

        System.out.println("Executing query: " + query);

        // Dummy login logic
        if (username.equals("admin") && password.equals("admin")) {
            return true;
        }
        return false;
    }
}