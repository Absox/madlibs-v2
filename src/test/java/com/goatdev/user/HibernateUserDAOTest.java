package com.goatdev.user;

import com.goatdev.ApplicationTestConfigs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.*;

/**
 * Integration tests for User DAO.
 * Created by ran on 4/19/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {ApplicationTestConfigs.class, HibernateUserDAO.class},
        loader = AnnotationConfigContextLoader.class)
public class HibernateUserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testCreate() {
        User newUser = new User("username", "password");
        assertNotNull(userDAO.createUser(newUser));
        User retrieved = userDAO.getUser("username");
        assertNotNull(retrieved);
        assertTrue(retrieved.authenticate("password"));
    }

    @Test
    public void testRetrieval() {
        User retrieved = userDAO.getUser("noSuchUser");
        assertNull(retrieved);
    }

    @Test
    public void testUpdate() {
        User newUser = new User("updateTestUser", "password");
        assertFalse(userDAO.updateUser(newUser));

        assertNotNull(userDAO.createUser(newUser));
        newUser.changePassword("newPassword");
        assertTrue(userDAO.updateUser(newUser));
        User retrieved = userDAO.getUser("updateTestUser");
        assertNotNull(retrieved);
        assertFalse(retrieved.authenticate("password"));
        assertTrue(retrieved.authenticate("newPassword"));
    }

}
