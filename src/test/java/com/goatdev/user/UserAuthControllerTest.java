package com.goatdev.user;

import com.goatdev.ApplicationTestConfigs;
import com.goatdev.auth.AuthTokenManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Functional tests for user auth controller.
 * Created by ran on 4/20/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                ApplicationTestConfigs.class,
                HibernateUserDAO.class,
                UserAuthController.class,
                AuthTokenManager.class,
        },
        loader = AnnotationConfigContextLoader.class)
public class UserAuthControllerTest {

    @Autowired
    private UserAuthController userAuthController;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testRegistration() {
        ResponseEntity<UserAuthController.UserRegistrationResponse> response
                = userAuthController.register(new UserAuthController.UserRegistrationRequest("testUser", "password"));
        assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
        assertNotNull(response.getBody().getId());

        ResponseEntity<UserAuthController.UserRegistrationResponse> secondResponse
                = userAuthController.register(new UserAuthController.UserRegistrationRequest("anotherUser", "password"));
        assertEquals(secondResponse.getStatusCode(), HttpStatus.ACCEPTED);
        assertNotNull(secondResponse.getBody().getId());

        ResponseEntity<UserAuthController.UserRegistrationResponse> duplicate
                = userAuthController.register(new UserAuthController.UserRegistrationRequest("testUser", "differentPassword"));
        assertEquals(duplicate.getStatusCode(), HttpStatus.BAD_REQUEST);

        ResponseEntity<UserAuthController.UserRegistrationResponse> empty
                = userAuthController.register(new UserAuthController.UserRegistrationRequest());
        assertEquals(empty.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testLogin() {
        User loginTestUser = new User("loginTestUser", "password");
        assertNotNull(userDAO.createUser(loginTestUser));

        ResponseEntity<UserAuthController.UserLoginResponse> response
                = userAuthController.login(new UserAuthController.UserLoginRequest("loginTestUser", "password"));
        assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);

        ResponseEntity<UserAuthController.UserLoginResponse> noUserResponse
                = userAuthController.login(new UserAuthController.UserLoginRequest("noSuchUser", "password"));
        assertEquals(noUserResponse.getStatusCode(), HttpStatus.BAD_REQUEST);

        ResponseEntity<UserAuthController.UserLoginResponse> wrongPasswordResponse
                = userAuthController.login(new UserAuthController.UserLoginRequest("loginTestUser", "wrongPassword"));
        assertEquals(wrongPasswordResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}
