package com.fdu.capstone_instrument_shopping_center.controllers;

import com.fdu.capstone_instrument_shopping_center.Request.AuthenticationRequest;
import com.fdu.capstone_instrument_shopping_center.dto.UserDetailDto;
import com.fdu.capstone_instrument_shopping_center.dto.UserInfoDto;
import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
import com.fdu.capstone_instrument_shopping_center.response.AuthenticationResponse;
import com.fdu.capstone_instrument_shopping_center.response.Response;
import com.fdu.capstone_instrument_shopping_center.security.util.JwtUtil;
import com.fdu.capstone_instrument_shopping_center.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {
    UserInfoService userInfoService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @Autowired
    public UserController(UserInfoService userInfoService, AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userInfoService = userInfoService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthenticationResponse userRegister(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String jwt = "";
        String username = authenticationRequest.getUsername();
        // Use UsernamePasswordAuthenticationToken to pass username and password
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new AuthenticationResponse(false, "Username/Password error, please check credentials.");
        } catch (Exception e) {
            return new AuthenticationResponse(false, "Login Failed.");
        }
        // find user details if authentication passed
        UserInfo userInfo = userInfoService.findUserInfoByUsername(username);
        if (userInfo != null) {
            // generate JWT Token
            jwt = jwtUtil.generateToken(userInfo.getUsername(), authenticationRequest.getRole());
            // return jwt token to client
            return new AuthenticationResponse(true, "User: " + userInfo.getUsername() + "Success.", jwt, userInfo.getRole());
        } else {
            return new AuthenticationResponse(false, "UserInfo is empty.");
        }
    }

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody UserInfoDto userInfoDto) {
        UserInfo userInfo = userInfoService.register(userInfoDto);
        return new AuthenticationResponse(true,"User successfully registered!");
    }
}
