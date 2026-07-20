package com.mohit.demo.Scope;

import com.mohit.demo.StudentServer.Entity.Student;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class LPU {

    LPU() {
        System.out.println("LPU rank 1");
    }

    public void admission(Student student){
        System.out.println("Admission");
    }

    public void exam() {
        System.out.println("Examination");
    }

    public void prepClasses(Student student) {
        System.out.println("PEP Classes");
    }


}
