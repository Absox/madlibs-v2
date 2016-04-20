package com.goatdev.user;

import com.goatdev.ApplicationConfigs;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for user login.
 * Created by ran on 4/19/16.
 */
public class UserTest {

    private User testUser;

    @Before
    public void init() {
        testUser = new User("absox", "password");
    }

    @Test
    public void userLoginTest() {
        assertTrue(testUser.authenticate("password"));
    }

    @Test
    public void rigorousUserLoginTest() {

        Random rng = new Random(0);

        for (int c = 4; c < 10; c++) {

            byte[] bytes = new byte[c];
            for (int d = 0; d < 50; d++) {
                rng.nextBytes(bytes);
                String password = new String(bytes);
                User instanceUser = new User("username", password);
                assertTrue(instanceUser.authenticate(password));
                bytes[0]++;
                String modifiedPassword = new String(bytes);
                if (!modifiedPassword.equals(password)) {
                    assertFalse(instanceUser.authenticate(modifiedPassword));
                }
            }
        }
    }

    @Test
    public void changePasswordTest() {
        Random rng = new Random(0);
        User instanceUser = new User("username", "password");

        for (int c = 4; c < 10; c++) {
            byte[] bytes = new byte[c];
            for (int d = 0; d < 50; d++) {
                rng.nextBytes(bytes);
                String password = new String(bytes);
                instanceUser.changePassword(password);
                assertTrue(instanceUser.authenticate(password));
            }
        }
    }

}
