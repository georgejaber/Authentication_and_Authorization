package com.example.authdemo.AuthRequirement;


import com.example.authdemo.DTO.AuthRequest;
import com.example.authdemo.DTO.AuthResponse;
import com.example.authdemo.DTO.UserDTO;
import com.example.authdemo.User.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> save(@RequestBody @NotNull UserDTO userDTO) throws Exception {
       User user = new User(userDTO.getEmail(),userDTO.getPassword(),userDTO.getRole());
       return new ResponseEntity<>(service.register(user), HttpStatus.CREATED);
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
