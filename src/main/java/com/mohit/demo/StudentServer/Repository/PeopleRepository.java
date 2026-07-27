package com.mohit.demo.StudentServer.Repository;

import com.mohit.demo.StudentServer.Entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<People,Integer> {
    Optional<People> findByUsername(String username);
}