package com.fdu.capstone_instrument_shopping_center.response;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class AuthenticationResponse extends Response{

    String jwtToken;
    String role;

    public AuthenticationResponse(boolean success, String message) {
        super(success,message);
        log.info("Authentication result:{}. Message: {}.", success,  message);

    }

    public AuthenticationResponse(boolean success, String message, String jwtToken) {
        super(success, message);
        this.jwtToken = jwtToken;
        log.info("Authentication result:{}. Message: {}, token: {}.", success, message, jwtToken);
    }

    public AuthenticationResponse(boolean success, String message, String jwtToken, String role) {
        super(success, message);
        this.jwtToken = jwtToken;
        this.role = role;
        log.info("Authentication result:{}. Message: {}, token: {}.", success, message, jwtToken);
    }

}
