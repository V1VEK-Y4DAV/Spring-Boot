package com.mohit.demo.StudentServer.Controller;

import com.mohit.demo.StudentServer.Entity.People;
import com.mohit.demo.StudentServer.Repository.PeopleRepository;
import com.mohit.demo.StudentServer.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleRepository repository;

    @Autowired
    private JwtService jwtService;

    // Signup
    @PostMapping("/signup")
    public String signup(@RequestBody People people) {

        repository.save(people);

        return "User Created Successfully";
    }

    // Login
    @PostMapping("/login")
    public String login(@RequestBody People people) {

        Optional<People> dbUser =
                repository.findByUsername(people.getUsername());

        if(dbUser.isEmpty()) {
            return "User Not Found";
        }

        if(!dbUser.get().getPassword().equals(people.getPassword())) {
            return "Wrong Password";
        }

        String token =
                jwtService.generateToken(people.getUsername());

        return token;
    }

    // Protected API
    @GetMapping("/{id}")
    public Object getPeople(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String authHeader) {

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "JWT Missing";
        }

        String token =
                authHeader.substring(7);

        if(!jwtService.validateToken(token)) {
            return "Invalid Token";
        }

        return repository.findById(id).orElse(null);

    }

}
