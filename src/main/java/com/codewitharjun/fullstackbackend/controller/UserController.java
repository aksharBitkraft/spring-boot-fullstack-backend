package com.codewitharjun.fullstackbackend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.codewitharjun.fullstackbackend.exception.UserNotFoundException;
import com.codewitharjun.fullstackbackend.model.User;
import com.codewitharjun.fullstackbackend.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        System.out.println("creatting user....");
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        System.out.println("getting all users...");
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        System.out.println("getting the user by id " + id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@PathVariable Long id, @RequestBody User updateUserData) {
        System.out.println("updating the user with id " + id);
        // first find the user
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        System.out.println("the found object " + existingUser.toString());
        // update the user data
        existingUser.setEmail(updateUserData.getEmail());
        existingUser.setName(updateUserData.getName());
        existingUser.setUsername(updateUserData.getUsername());

        return userRepository.save(existingUser);
    }

    // Delete the user
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        // check if user dont exist
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        // delete the user
        System.out.println("deleting the user with id " + id);
        userRepository.deleteById(id);
        return ResponseEntity.ok("User with ID " + id + " has been deleted successfully.");

    }

}
