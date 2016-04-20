package com.goatdev.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for user auth.
 * Created by ran on 4/19/16.
 */
@RestController
public class UserAuthController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/api/user/register", produces = "application/json")
    @ResponseBody
    public ResponseEntity<UserRegistrationResponse> register(@RequestBody UserRegistrationRequest request) {

        if (request.username == null || request.password == null) {

        }

        return new ResponseEntity<UserRegistrationResponse>(new UserRegistrationResponse(0), HttpStatus.ACCEPTED);
    }

    public static class UserRegistrationRequest {

        String username;

        String password;

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

    }

    public static class UserRegistrationResponse {

        int id;

        public UserRegistrationResponse(int id) {
            this.id = id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

    }

}
