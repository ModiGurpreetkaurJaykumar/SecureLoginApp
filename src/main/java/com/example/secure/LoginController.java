package com.example.secure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private static final String DB_URL      = "jdbc:h2:mem:testdb";
    private static final String DB_USER     = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password) {

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return ResponseEntity.ok("Login successful. Welcome, " + username + "!");
            } else {
                return ResponseEntity.status(401).body("Invalid credentials.");
            }

        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Database error.");
        }
    }
}