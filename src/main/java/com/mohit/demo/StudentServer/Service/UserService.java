package com.mohit.demo.StudentServer.Service;

import com.mohit.demo.StudentServer.Entity.User;
import com.mohit.demo.StudentServer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public User createUser(User user){

        user.setPassword(
                encoder.encode(user.getPassword())
        );

        user.setRoles(List.of("USER"));

        return repository.save(user);
    }

    public List<User> getAll(){
        return repository.findAll();
    }

    public User getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public User update(Long id, User updated){

        User user = repository.findById(id).orElseThrow();

        user.setUserName(updated.getUserName());

        if(updated.getPassword()!=null){
            user.setPassword(
                    encoder.encode(updated.getPassword())
            );
        }

        return repository.save(user);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}