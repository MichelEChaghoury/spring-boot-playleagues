package org.ultims.playleagues.contract.v1.response;

import org.ultims.playleagues.model.User;

public class AuthenticationResponse {

    private User user;
    private String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse [token=" + token + ", user=" + user + "]";
    }

}
