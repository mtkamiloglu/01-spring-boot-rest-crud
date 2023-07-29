package com.mtkcode.demo.rest;

import com.mtkcode.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    List<Student> theStudents = new ArrayList<>();

    @PostConstruct
    public void loadData(){

        theStudents.add(new Student("Hamza", "Küçükkaraca"));
        theStudents.add(new Student("Enes", "Aşçıoğlu"));
        theStudents.add(new Student("Alpay", "Şerifler"));
        theStudents.add(new Student("Eren", "Esmer"));
    }

    @GetMapping("/students")
    public List<Student> getStudents(){

        return theStudents;
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable int studentId) {

        if (theStudents.size() <= studentId || studentId < 0) {
            throw new StudentNotFoundException("Student id not found: " + studentId);
        }

        return theStudents.get(studentId);
    }


    // adding an exception handler
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){

        StudentErrorResponse errorResponse = new StudentErrorResponse();

        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
