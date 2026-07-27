package com.mohit.demo.StudentServer.Controller;

import com.mohit.demo.StudentServer.Entity.User;
import com.mohit.demo.StudentServer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/create")
    public User create(@RequestBody User user){
        return service.createUser(user);
    }

    @GetMapping
    public List<User> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id){

        return service.getById(id);
    }

    @PutMapping("/{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody User user){

        return service.update(id,user);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){

        service.delete(id);

        return "Deleted";
    }

}
