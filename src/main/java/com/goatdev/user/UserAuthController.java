package com.goatdev.user;

import com.goatdev.auth.AuthToken;
import com.goatdev.auth.AuthTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user auth.
 * Created by ran on 4/19/16.
 */
@RestController
public class UserAuthController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AuthTokenManager authTokenManager;

    @RequestMapping(value = "/api/user/register", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<UserRegistrationResponse> register(@RequestBody UserRegistrationRequest request) {

        if (request.username == null || request.password == null) {
            return new ResponseEntity<>(new UserRegistrationResponse("Required parameters not found"), HttpStatus.BAD_REQUEST);
        }

        if (userDAO.getUser(request.username) != null) {
            return new ResponseEntity<>(new UserRegistrationResponse("Username taken"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new UserRegistrationResponse(userDAO.createUser(new User(request.username, request.password))), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/api/user/login", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        if (request.username == null || request.password == null) {
            return new ResponseEntity<>(new UserLoginResponse("Required parameters not found"), HttpStatus.BAD_REQUEST);
        }

        User loginUser = userDAO.getUser(request.username);

        if (loginUser == null) {
            return new ResponseEntity<>(new UserLoginResponse("User does not exist!"), HttpStatus.BAD_REQUEST);
        }

        if (loginUser.authenticate(request.password)) {
            AuthToken newToken = authTokenManager.issueToken(loginUser);
            return new ResponseEntity<>(new UserLoginResponse(newToken), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(new UserLoginResponse("Password invalid!"), HttpStatus.BAD_REQUEST);
        }
    }

    public static class UserRegistrationRequest {

        String username;
        String password;

        public UserRegistrationRequest() {
        }

        public UserRegistrationRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

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

        Integer id;

        String reason;

        public UserRegistrationResponse(String reason) {
            this.reason = reason;
        }

        public UserRegistrationResponse(Integer id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getReason() {
            return reason;
        }
    }

    public static class UserLoginRequest {
        String username;
        String password;

        public UserLoginRequest() {
        }

        public UserLoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class UserLoginResponse {
        String reason;
        String authToken;

        public UserLoginResponse() {
        }

        public UserLoginResponse(AuthToken token) {
            authToken = token.getValue();
        }

        public UserLoginResponse(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
