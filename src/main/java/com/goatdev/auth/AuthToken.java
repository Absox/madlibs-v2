package com.goatdev.auth;

import com.goatdev.user.User;

import java.util.Date;

/**
 * Authentication token class.
 * Created by ran on 4/20/16.
 */
public class AuthToken {

    private Date expiration;

    private String value;

    private User user;

    public AuthToken(Date expiration, String value, User user) {
        this.expiration = expiration;
        this.value = value;
        this.user = user;
    }

    public boolean isExpired() {
        return expiration.before(new Date());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

}
