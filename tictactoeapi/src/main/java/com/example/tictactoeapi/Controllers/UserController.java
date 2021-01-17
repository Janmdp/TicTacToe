package com.example.tictactoeapi.Controllers;

import classes.User;
import com.example.tictactoeapi.Models.UserModel;
import com.example.tictactoeapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/login")
    public User login(@RequestParam String name, @RequestParam String password){
        return service.login(name,password);
    }

}
