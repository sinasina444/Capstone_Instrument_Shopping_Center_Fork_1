package com.fdu.capstone_instrument_shopping_center.Request;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class AuthenticationRequest {

    private String username;

    private String password;

    private String role;
}
