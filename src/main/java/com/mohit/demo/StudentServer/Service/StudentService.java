package com.mohit.demo.StudentServer.Service;

import com.mohit.demo.StudentServer.DTO.CreateStudentRequestDTO;
import com.mohit.demo.StudentServer.DTO.CreateStudentResponseDTO;
import com.mohit.demo.StudentServer.DTO.UpdateStudentRequestDTO;
import com.mohit.demo.StudentServer.DTO.UpdateStudentResponseDTO;
import com.mohit.demo.StudentServer.Entity.Student;
import com.mohit.demo.StudentServer.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StudentService {
    StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDTO studentValidate(CreateStudentRequestDTO createStudentRequestDTO) {

        Student student = mapToStudent(createStudentRequestDTO);
        studentRepository.save(student);
        return mapToResponseDTO(student);
    }

    public Student getStudentById(int id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.get();
    }

    public UpdateStudentResponseDTO studentUpdate(
            int id,
            UpdateStudentRequestDTO updateStudentRequestDTO) {

        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return null;
        }

        student.setName(updateStudentRequestDTO.getName());
        student.setAge(updateStudentRequestDTO.getAge());
        student.setUpdatedAt(LocalDateTime.now());

        studentRepository.save(student);

        return mapToUpdateResponseDTO(student);
    }

    public Student deleteStudent(int id) {
        Student result = studentRepository.findById(id).orElse(null);
        if(result == null) {
            return null;
        }
        studentRepository.delete(result);
        return result;
    }

    private Student mapToStudent(CreateStudentRequestDTO createStudentRequestDTO) {
        Student student = new Student();

        student.setName(createStudentRequestDTO.getName());
        student.setAge(createStudentRequestDTO.getAge());
        student.setDepartment(createStudentRequestDTO.getDepartment());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        return student;
    }

    private CreateStudentResponseDTO mapToResponseDTO(Student student) {
        CreateStudentResponseDTO createStudentResponseDTO = new CreateStudentResponseDTO();
        createStudentResponseDTO.setId(student.getId());
        createStudentResponseDTO.setName(student.getName());
        createStudentResponseDTO.setAge(student.getAge());
        createStudentResponseDTO.setDepartment(student.getDepartment());

        return createStudentResponseDTO;

    }

    private UpdateStudentResponseDTO mapToUpdateResponseDTO(Student student) {

        UpdateStudentResponseDTO dto = new UpdateStudentResponseDTO();

        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        dto.setDepartment(student.getDepartment());

        return dto;
    }


}