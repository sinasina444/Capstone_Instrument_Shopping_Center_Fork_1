package com.fdu.capstone_instrument_shopping_center.response;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class UserProfileResponse extends Response{

    String username;
    String role;
    String email;
    String address;
    String phoneNumber;


    public UserProfileResponse(boolean success, String message) {
        super(success,message);
        log.info("UserProfile response:{}. Message: {}.", success,  message);

    }

    public UserProfileResponse(boolean success, String message, String username, String role,
                               String email, String address, String phoneNumber) {
        super(success, message);
        this.username = username;
        this.role = role;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}

