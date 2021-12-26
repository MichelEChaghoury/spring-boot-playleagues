package org.ultims.playleagues.contract.v1.request;

public class AuthenticationRequest {

    private final String userName;
    private final String password;

    public AuthenticationRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest [password=" + password + ", userName=" + userName + "]";
    }

}
