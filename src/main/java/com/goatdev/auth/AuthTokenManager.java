package com.goatdev.auth;

import com.goatdev.user.User;
import com.goatdev.util.HashQueue;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Authentication token management component.
 * Created by ran on 4/20/16.
 */
@Component
public class AuthTokenManager {

    private HashQueue<String, AuthToken> tokenHashQueue;

    public AuthTokenManager() {
        this.tokenHashQueue = new HashQueue<>();
    }

    public AuthToken issueToken(User user) {
        clearExpiredTokens();
        AuthToken newToken = new AuthToken(new Date(System.currentTimeMillis() + 1000 * 60 * 60), UUID.randomUUID().toString(), user);
        tokenHashQueue.enqueue(newToken.getValue(), newToken);
        return newToken;
    }

    public AuthToken authenticate(String authTokenValue) {
        clearExpiredTokens();
        AuthToken oldToken = tokenHashQueue.remove(authTokenValue);
        if (oldToken == null) return null;
        return issueToken(oldToken.getUser());
    }

    private void clearExpiredTokens() {
        while (!tokenHashQueue.isEmpty() && tokenHashQueue.peek().isExpired()) {
            tokenHashQueue.dequeue();
        }
    }
}
