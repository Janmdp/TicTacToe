package com.example.tictactoeapi.Controllers;

import classes.User;
import com.example.tictactoeapi.Models.AuthRequest;
import com.example.tictactoeapi.Models.UserModel;
import com.example.tictactoeapi.Services.UserService;
import com.example.tictactoeapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService service;
    @GetMapping("/users")
    public List<User> list(){
        return service.listAll();
    }
    @Autowired
    private JwtUtil jwtUtil;

    private AuthenticationManager authenticationManager;


    @GetMapping("/user/{id}")
    public User getById(@PathVariable Integer id){
        return service.get(id);
    }

    @GetMapping("/user")
    public User login(@RequestParam String name, @RequestParam String password){
        User user = service.login(name,password);
        if(user != null){
            user.setJwtToken(jwtUtil.generateToken(user.getName()));
        }
        return user;
    }
    @PostMapping("/user")
    public void register(@RequestBody User user) {
        service.register(user.getName(), user.getPassword());
    }

    @PostMapping("/test")
    public void getToken(@RequestBody AuthRequest authRequest){
        System.out.println(authRequest);
    }

}
