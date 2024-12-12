package com.javazerozahar.stock_exchange.controllers;


import com.javazerozahar.stock_exchange.model.dto.UserDTO;
import com.javazerozahar.stock_exchange.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.getUserDTO(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.addUser(userDTO), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}
