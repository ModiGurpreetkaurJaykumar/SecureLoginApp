import com.example.demo.LoginService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        boolean success = service.login(username, password);

        if (success) {
            return "Login Successful";
        } else {
            return "Login Failed";
        }
    }
}