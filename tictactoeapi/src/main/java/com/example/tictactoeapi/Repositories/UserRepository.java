package com.example.tictactoeapi.Repositories;

import com.example.tictactoeapi.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

}
