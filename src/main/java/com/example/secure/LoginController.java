package com.example.secure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

/**
 * LoginController — VULNERABLE VERSION (for DevSecOps TD demonstration)
 *
 * Intentional security issues introduced for scanning demo:
 *   1. Hardcoded database password (detected by Gitleaks)
 *   2. SQL query built by string concatenation (detected by SpotBugs as SQL_INJECTION)
 *
 * These will be flagged by the CI/CD pipeline and fixed in the corrected version.
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    // =====================================================================
    // VULNERABILITY 1: Hardcoded credential
    // Gitleaks will flag "DB_PASSWORD" as a secret pattern.
    // A real password must never be committed to source control.
    // =====================================================================
    private static final String DB_URL      = "jdbc:h2:mem:testdb";
    private static final String DB_USER     = "sa";
    private static final String DB_PASSWORD = "SuperSecret123!";   // <-- HARDCODED SECRET

    /**
     * POST /api/login?username=alice&password=pass123
     *
     * Returns 200 OK with a welcome message on success,
     * or 401 Unauthorized when credentials do not match.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password) {

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            // ==================================================================
            // VULNERABILITY 2: SQL injection via string concatenation
            // An attacker can supply username = ' OR '1'='1
            // to bypass authentication entirely.
            // SpotBugs (SQL_INJECTION_JDBC) will flag this pattern.
            // ==================================================================
            String query = "SELECT * FROM users WHERE username = '" + username
                         + "' AND password = '" + password + "'";

            Statement stmt = conn.createStatement();
            ResultSet rs   = stmt.executeQuery(query);

            if (rs.next()) {
                return ResponseEntity.ok("Login successful. Welcome, " + username + "!");
            } else {
                return ResponseEntity.status(401).body("Invalid credentials.");
            }

        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Database error: " + e.getMessage());
        }
    }
}
