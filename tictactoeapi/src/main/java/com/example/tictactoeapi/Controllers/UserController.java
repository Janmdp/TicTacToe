package com.example.tictactoeapi.Controllers;

import classes.User;
import com.example.tictactoeapi.Models.UserModel;
import com.example.tictactoeapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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



    @GetMapping("/user/{id}")
    public User getById(@PathVariable Integer id){
        return service.get(id);
    }

    @GetMapping("/user")
    public User login(@RequestParam String name, @RequestParam String password){
        return service.login(name,password);
    }
    @PostMapping("/user")
    public void register(@RequestParam String name, @RequestParam String password) {
        service.register(name,password);
    }


}
