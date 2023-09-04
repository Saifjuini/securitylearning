package com.Security.Security.Controller;


import com.Security.Security.Models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static  final List<User> STUDENTS = Arrays.asList(
            new User(1L,"saif"),
            new User(2L,"hama")
    );

    @GetMapping("/student/{studeentID}")
    public ResponseEntity<User> getUser(@PathVariable("studeentID" ) Long id ){
        User users = STUDENTS.stream()
                .filter(user ->  id.equals(user.getId()))
                .findFirst()
                .orElseThrow( () -> new RuntimeException("student not found" ));
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping("/students")
    public ResponseEntity<List<User>> getAllUsers(){


        return new ResponseEntity<>(STUDENTS,HttpStatus.OK);
    }

}
