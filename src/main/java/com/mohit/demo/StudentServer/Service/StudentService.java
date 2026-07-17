package com.mohit.demo.StudentServer.Service;

import com.mohit.demo.StudentServer.Entity.Student;
import com.mohit.demo.StudentServer.Repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service

public class StudentService {
    StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }
    public Student studentValidate(Student student){
        int id= student.getId();
        String name= student.getName();
        int age= student.getAge();
        String dep= student.getDep();
        if(id<0 || age<0 || name==null || dep==null){
            return null;
        }
        studentRepository.save(student);
        return student;
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(int id, Student student) {
        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent == null) {
            return null;
        }

        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setDep(student.getDep());

        studentRepository.save(existingStudent);

        return existingStudent;
    }

    public boolean deleteStudent(int id) {
        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return false;
        }

        studentRepository.delete(student);
        return true;
    }
}
