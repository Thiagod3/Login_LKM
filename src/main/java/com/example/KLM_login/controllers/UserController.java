package com.example.KLM_login.controllers;

import ch.qos.logback.core.model.Model;
import com.example.KLM_login.models.User;
import com.example.KLM_login.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.Objects;

@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository= userRepository;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @RequestMapping("/")
    public String index(){

        String username = "admin";

        User existingUser = this.userRepository.findByUsername(username);
        if (existingUser == null) {
            String checkIn = "INSERT INTO user(password, username) VALUES ('admin', 'admin')";
            jdbcTemplate.execute(checkIn);
        }

        return "index";
    }

@PostMapping("/LKM")
public String LKM(@RequestParam String username, @RequestParam String password, Model model) {
    User user = this.userRepository.findByUsernameAndPassword(username, password);

    if(user == null){
        return "index";
    }
    return "apiTest";
    }

@PostMapping("/changePassword")
public String changePassword(){
        return "home";
}

@PostMapping("/reSignIn")
public String reSignIn(@RequestParam String newPassword, @RequestParam String newPasswordCheck, Model model){
        if (!Objects.equals(newPassword, newPasswordCheck)){
            return "wrong";
        }
    String username = "admin";

    String sql = "UPDATE user SET password = ? WHERE username = ?";
    jdbcTemplate.update(sql, newPassword, username);

    return "index";
    }
}
