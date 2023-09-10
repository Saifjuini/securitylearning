package com.Security.Security.Controller;

import com.Security.Security.Models.User;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/managment/users")
public class ManagmentUserController {
    private static  final List<User> STUDENTS = Arrays.asList(
            new User(1L,"saif"),
            new User(2L,"hama")
    );
    @GetMapping("/students")
    public List<User> getAllStudents(){
        return STUDENTS;
    }
    @PostMapping("/newStudent")
    public void addStudent(@RequestBody User user){
        System.out.println(user);
    }
   @DeleteMapping(path = "/deleteUser/{studentId}")
    public void deleteUser(@PathVariable("studentId") Integer userId){
        System.out.println(userId);
    }
    @PutMapping(path = "/updateUser/{studentId}")
    public void updateUser(@PathVariable("studentId") Integer userId ,@RequestBody User user){
        System.out.println(String.format("%s %s" , userId, user));
    }
}
