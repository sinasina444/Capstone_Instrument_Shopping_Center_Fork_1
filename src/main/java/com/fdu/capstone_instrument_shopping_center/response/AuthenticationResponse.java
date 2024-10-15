package com.fdu.capstone_instrument_shopping_center.response;

public class AuthenticationResponse extends Response{

    String jwtToken;

    public AuthenticationResponse(boolean success, String jwtToken) {
        super(success);
        this.jwtToken = jwtToken;
    }

    public AuthenticationResponse(boolean success) {
        super(success);
    }

}
