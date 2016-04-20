package com.goatdev.auth;

import com.goatdev.user.User;

import java.util.Date;
import java.util.UUID;

/**
 * Authentication token class.
 * Created by ran on 4/20/16.
 */
public class AuthToken {

    private Date expiration;

    private String value;

    private User user;

    public AuthToken() {
    }

    public AuthToken(Date expiration, String value, User user) {
        this.expiration = expiration;
        this.value = value;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
