package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController // This means that this class is a Controller
@RequestMapping(path="/user") // This means URL's start with /demo (after Application path)
public class MainController {
  private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

  @PostMapping() // Map ONLY POST Requests
  //@ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserModel> addNewUser (
          @RequestParam String id,
          @RequestParam String username,
          @RequestParam String firstname,
          @RequestParam String lastname,
          @RequestParam String email,
          @RequestParam String password,
          @RequestParam String phone) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    UserModel user = new UserModel();
    user.setId(id);

    user.setUsername(username);
    user.setFirstname(firstname);
    user.setLastname(lastname);
    user.setEmail(email);
    user.setPassword(password);
    user.setPhone(phone);
    user.setUserstatus(0);

    try {
      UserService obj = new UserService();
      boolean ret = obj.create(user);
      obj.close();
      if ( ret == true ) {
        return new ResponseEntity<>(user,HttpStatus.CREATED);
      } else {
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
      }
    } catch( Exception ex) {
      LOG.warn("handleException",ex);
      return new ResponseEntity<>(null,HttpStatus.NOT_IMPLEMENTED);
    }
  }

  @PutMapping(path="{username}")
  public ResponseEntity<UserModel> updateUser (
          @PathVariable String username,
          @RequestParam String id,
          @RequestParam String firstname,
          @RequestParam String lastname,
          @RequestParam String email,
          @RequestParam String password,
          @RequestParam String phone) {

    UserModel user = new UserModel();
    user.setId(id);
    user.setUsername(username);
    user.setFirstname(firstname);
    user.setLastname(lastname);
    user.setEmail(email);
    user.setPassword(password);
    user.setPhone(phone);
    user.setUserstatus(0);

    try {
      UserService obj = new UserService();
      boolean ret = obj.update(user);
      obj.close();
      if ( ret == true ) {
        return new ResponseEntity<>(user,HttpStatus.OK);
      } else {
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
      }
    } catch( Exception ex) {
      LOG.warn("handleException",ex);
      return new ResponseEntity<>(null,HttpStatus.NOT_IMPLEMENTED);
    }
  }

  @DeleteMapping(path="{username}")
  //@ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<String>  deleteUser (
          @PathVariable String username ) {

    UserModel user = new UserModel();

    user.setUsername(username);

    try {
      UserService obj = new UserService();
      boolean ret = obj.delete(user);
      obj.close();
      if ( ret == true ) {
        return new ResponseEntity<>("",HttpStatus.OK);
      } else {
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
      }
    } catch( Exception ex) {
      LOG.warn("handleException",ex);
      return new ResponseEntity<>(null,HttpStatus.NOT_IMPLEMENTED);
    }
  }

  @GetMapping(path="{username}")
  public ResponseEntity<UserModel> readUser (
          @PathVariable String username ) {

    try {
      UserService obj = new UserService();
      UserModel ret = obj.get(username);
      obj.close();
      if ( ret != null ) {
        return new ResponseEntity<>(ret,HttpStatus.OK);
      } else {
        return new ResponseEntity<>(ret,HttpStatus.NOT_FOUND);
      }
    } catch( Exception ex) {
      LOG.warn("handleException",ex);
      return null;
    }
  }

  @GetMapping(path="/list")
  public ResponseEntity<Iterable<UserModel>> getAllUsers() {
    // This returns a JSON or XML with the users

    try {
      UserService obj = new UserService();
      ArrayList<UserModel> lst = obj.list();
      obj.close();
      if ( lst.size() > 0 ) {
        return new ResponseEntity<>(lst,HttpStatus.OK);
      } else {
        return new ResponseEntity<>(lst,HttpStatus.NOT_FOUND);
      }
    } catch( Exception ex) {
      return null;
    }
  }
}