package com.mohit.demo.StudentServer.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateStudentRequestDTO {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
    @Min(value = 1, message = "Age must be greater than 0")
    private int age;
    @NotBlank(message = "Department cannot be empty")
    @Size(min = 2, max = 50, message = "Department must be between 2 and 50 characters")
    private String department;
}
