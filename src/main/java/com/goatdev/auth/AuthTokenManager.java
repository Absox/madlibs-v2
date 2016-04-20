package com.goatdev.auth;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Authentication token management component.
 * Created by ran on 4/20/16.
 */
@Component
public class AuthTokenManager {

    private HashMap<String, AuthToken> tokenMap;
    private Queue<AuthToken> expirationQueue;

    public AuthTokenManager() {
        tokenMap = new HashMap<>();
        expirationQueue = new LinkedList<>();
    }
}
