package com.tcs.training.controller;

import com.tcs.training.bean.Signup;
import com.tcs.training.repository.SignupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ControllerClass {

    @Autowired
    SignupRepo signupRepo;

    @GetMapping("/")
    public String publicMsg(){
        return "Public Msg..";
    }

    @GetMapping("/user")
    public String userMethod(){
        return "User Method Msg..";
    }

    @GetMapping("/user/{id}")
    public Optional<Signup> anotherUserMethod(@PathVariable Long id){
        return signupRepo.findById(id);
    }

}
