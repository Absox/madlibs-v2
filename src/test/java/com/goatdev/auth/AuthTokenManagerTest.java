package com.goatdev.auth;

import com.goatdev.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Integration tests for Auth token manager.
 * Created by ran on 4/20/16.
 */
public class AuthTokenManagerTest {

    private AuthTokenManager authTokenManager;
    @Before
    public void init() {
        authTokenManager = new AuthTokenManager();
    }

    @Test
    public void testTokenIssuances() {
        User testUser = new User("username", "password");
        AuthToken token = authTokenManager.issueToken(testUser);

        AuthToken newToken = authTokenManager.authenticate(token.getValue());
        assertNotNull(newToken);
        assertNull(authTokenManager.authenticate(token.getValue()));
    }
}
