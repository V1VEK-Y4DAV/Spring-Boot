package com.mohit.demo.StudentServer.Controller;

import com.mohit.demo.StudentServer.Entity.Student;
import com.mohit.demo.StudentServer.Service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    //1. to store the data
    StudentService studentService;
    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }
    @PostMapping("/create")
    public ResponseEntity<Student> storeStudent(@RequestBody Student student){
        Student result = studentService.studentValidate(student);
        if(result==null){
           return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(201).body(result);





    }
}
