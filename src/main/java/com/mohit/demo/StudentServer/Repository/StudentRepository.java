package com.mohit.demo.StudentServer.Repository;

import com.mohit.demo.StudentServer.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Integer> {

}
