package com.example.authdemo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    AuthControllerService service;
    public AuthController(AuthControllerService service) {
        this.service = service;
    }

    @PostMapping ("/auth")
    public AuthResponse login(@RequestBody AuthRequest request)
    {
        return service.login(request);
    }

    @GetMapping("/")
    public String viewer()
    {
        return "<h1>welcome</h1>";
    }

    @GetMapping("/user")
    public String user()
    {
        return "<h1>Welcome User</h1>";
    }

    @GetMapping("/admin")
    public String admin()
    {
        return "<h1>Welcome Admin</h1>";
    }
}
