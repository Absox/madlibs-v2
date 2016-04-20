package com.goatdev.user;

/**
 * Data access object for User.
 * Created by ran on 4/19/16.
 */
public interface UserDAO {

    public boolean saveUser(User user);

    public User getUser(String username);


}
