package com.goatdev.auth;

import com.goatdev.user.User;
import org.springframework.stereotype.Component;

import java.util.*;

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

    /**
     * Issues an auth token.
     * @param user User to issue token for.
     * @return Auth token issued.
     */
    public AuthToken issueToken(User user) {
        clearExpiredTokens();

        AuthToken newToken = new AuthToken(
                new Date(System.currentTimeMillis() + 1000 * 60 * 60),
                UUID.randomUUID().toString(), user);
        tokenMap.put(newToken.getValue(), newToken);
        expirationQueue.add(newToken);
        return newToken;
    }

    public AuthToken authenticate(String authTokenValue) {
        clearExpiredTokens();

        if (tokenMap.containsKey(authTokenValue)) {
            AuthToken oldToken = tokenMap.remove(authTokenValue);
            return issueToken(oldToken.getUser());
        } else {
            return null;
        }
    }

    /**
     * Clears all expired tokens.
     */
    private void clearExpiredTokens() {
        while (expirationQueue.peek() != null && expirationQueue.peek().getExpiration().after(new Date())) {
            AuthToken toRemove = expirationQueue.remove();
            if (tokenMap.containsKey(toRemove.getValue())) tokenMap.remove(toRemove.getValue());
        }
    }
}
