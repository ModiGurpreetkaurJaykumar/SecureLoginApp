# SecureLoginApp

A minimal Java Spring Boot REST API demonstrating **DevSecOps practices** by integrating security tools in a CI/CD pipeline.  
The project includes intentional vulnerabilities to illustrate detection and mitigation using **Gitleaks**, **SpotBugs**, and **OWASP Dependency Check**.

---

## Project Overview

- **Endpoint:**  
  `POST /api/login?username={value}&password={value}`
- Accepts user credentials for login.
- Demonstrates simple authentication logic.
- Intentionally includes security issues for demonstration:
  - Hardcoded secret (`dbPassword`)
  - String-concatenated query (SQL injection pattern)

---

## Pipeline / Workflow

The project integrates a **GitHub Actions CI/CD pipeline**:

- **Location:** `.github/workflows/devsecops.yml`
- **Triggered on:** Push to `main` branch

### Security Controls Implemented

| Tool | Type | Stage | Purpose |
|------|------|-------|---------|
| Gitleaks | Secret Scanning | Pre-build | Detects hardcoded secrets in source code and git history |
| SpotBugs | SAST | Build phase | Detects insecure code patterns (e.g., SQL injection, unused variables) |
| OWASP Dependency Check | SCA | Build phase | Scans dependencies for known vulnerabilities |

---

## Vulnerabilities

### Before Fix

```java
private String dbPassword = "admin123"; // Hardcoded secret (unused variable)
String query = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";
```

**Impact:**
- Hardcoded secret is permanently stored in git history
- SQL query is vulnerable to injection attacks
- SpotBugs detects an unread field (`URF_UNREAD_FIELD`), pipeline fails

### After Fix

```java
// Removed hardcoded secret
String query = "SELECT * FROM users WHERE username=? AND password=?";
```

**Fixes Applied:**
- Removed unused hardcoded variable
- Improved query handling to prevent SQL injection
- Pipeline executes successfully

---

## Setup & Run Locally

1. **Clone the repository:**

```bash
git clone https://github.com/JayasriDhanapal/SecureLoginApp.git
cd SecureLoginApp
```

2. Ensure **Java 17** and **Maven** are installed.

3. **Build and run the project:**

```bash
mvn clean install
mvn spring-boot:run
```

4. **Test the endpoint** using a browser or Postman:

```
POST http://localhost:8080/api/login?username=admin&password=admin
```
