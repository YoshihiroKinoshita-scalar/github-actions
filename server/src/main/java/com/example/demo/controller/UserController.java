package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/user")
public class UserController {
  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @PostMapping()
  public ResponseEntity<User> addNewUser(@RequestBody User user) {

    try {
      boolean canCreate = userService.create(user);
      if ( canCreate == true ) {
        return new ResponseEntity<>(user, HttpStatus.CREATED);
      } else {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }
    } catch( Exception ex) {
      LOG.warn("handleException", ex);
      return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
    }
  }

  @PutMapping("/{username}")
  public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String username) {

    try {
      boolean canUpdate = userService.update(user);
      if ( canUpdate == true ) {
        return new ResponseEntity<>(user, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
    } catch( Exception ex) {
      LOG.warn("handleException", ex);
      return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
    }
  }

  @DeleteMapping("/{username}")
  public ResponseEntity<String>  deleteUser(@PathVariable String username) {

    User user = User.builder().username(username).build();

    try {
      boolean canDelete = userService.delete(user);
      if ( canDelete == true ) {
        return new ResponseEntity<>("", HttpStatus.OK);
      } else {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
    } catch( Exception ex) {
      LOG.warn("handleException", ex);
      return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
    }
  }

  @GetMapping("/{username}")
  public ResponseEntity<User> readUser(@PathVariable String username) {

    try {
      User userInfo = userService.get(username);
      if ( userInfo != null ) {
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(userInfo, HttpStatus.NOT_FOUND);
      }
    } catch( Exception ex) {
      LOG.warn("handleException", ex);
      return null;
    }
  }

  @GetMapping("/list")
  public ResponseEntity<Iterable<User>> getAllUsers() {
    try {
      ArrayList<User> userList = userService.list();
      if ( userList.size() > 0 ) {
        return new ResponseEntity<>(userList, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(userList, HttpStatus.NOT_FOUND);
      }
    } catch( Exception ex) {
      return null;
    }
  }
}