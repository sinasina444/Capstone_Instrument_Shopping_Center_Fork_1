//package com.fdu.capstone_instrument_shopping_center.controllers;
//
//import com.fdu.capstone_instrument_shopping_center.Request.AuthenticationRequest;
//import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
//import com.fdu.capstone_instrument_shopping_center.response.AuthenticationResponse;
//import com.fdu.capstone_instrument_shopping_center.security.util.JwtUtil;
//import com.fdu.capstone_instrument_shopping_center.services.UserInfoService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/Authentication")
//@Slf4j
//public class AuthenticationController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private UserInfoService userInfoService;
//
//    @PostMapping("/register")
//    public AuthenticationResponse userRegister(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//        UserInfo userInfo;
//        String jwt = "";
//        String username = authenticationRequest.getUsername();
//        // Use UsernamePasswordAuthenticationToken to pass username and password
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, authenticationRequest.getPassword())
//            );
//        } catch (BadCredentialsException e) {
//            throw new Exception("Credentials are invalid.", e);
//        }
//        // find user details if authentication passed
//        userInfo = userInfoService.findUserInfoByUsername(username);
//        if (userInfo != null) {
//            // generate JWT Token
//            jwt = jwtUtil.generateToken(userInfo.getUsername(), authenticationRequest.getRole());
//            // return jwt token to client
//            return new AuthenticationResponse(true, jwt);
//        } else {
//            return new AuthenticationResponse(false);
//
//        }
//
//    }
//}
