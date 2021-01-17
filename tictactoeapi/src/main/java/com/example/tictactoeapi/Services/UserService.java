package com.example.tictactoeapi.Services;

import classes.User;
import com.example.tictactoeapi.Models.UserModel;
import com.example.tictactoeapi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> listAll(){
        List<User> toReturn = new ArrayList<>();
        for (UserModel model:repo.findAll()) {
            User user = new User(model.getId(),model.getName(), model.getPassword());
            toReturn.add(user);
        }
        return toReturn;
    }

    public void save(UserModel user){
        repo.save(user);
    }

    public User get(Integer id){
        UserModel model = repo.findById(id).get();
        User user = new User(model.getId(), model.getName(), model.getPassword());
        return user;
    }

    public void delete(Integer id){
        repo.deleteById(id);
    }

    public User login(String name, String password){
        User user = new User();
        for (UserModel model: repo.findAll()){
            if(model.getName().equals(name) && model.getPassword().equals(password)){
                user = new User(model.getId(), model.getName(), model.getPassword());
                return user;
            }
        }
        return user;
    }
}
