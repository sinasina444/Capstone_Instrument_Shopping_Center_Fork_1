package com.fdu.capstone_instrument_shopping_center.controllers;

import com.fdu.capstone_instrument_shopping_center.dto.UserInfoDto;
import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
import com.fdu.capstone_instrument_shopping_center.repositories.UserInfoRepository;
import com.fdu.capstone_instrument_shopping_center.response.Response;
import com.fdu.capstone_instrument_shopping_center.response.UserProfileResponse;
import com.fdu.capstone_instrument_shopping_center.security.util.JwtUtil;
import com.fdu.capstone_instrument_shopping_center.services.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/UserProfile")
@Slf4j
public class UserProfileController {

    UserInfoRepository userInfoRepository;

    UserInfoService userInfoService;

    JwtUtil jwtUtil;

    @Autowired
    public UserProfileController(UserInfoRepository userInfoRepository,
                                 UserInfoService userInfoService,
                                 JwtUtil jwtUtil) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoService = userInfoService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/getUserInfoByUsername")
    public Response getUserInfoByUsername(@RequestBody UserInfoDto userInfoDto) {
        log.info("Test");
        if(userInfoDto.getUsername() == null || userInfoDto.getUsername().isEmpty()) {
            return new Response(false, "Failed to retrieve username in getUserInfo request.");
        }
        UserInfo userInfo = userInfoService.findUserInfoByUsername(userInfoDto.getUsername());
        if(userInfo != null) {
            return new UserProfileResponse(true,
                    "UserInfo Retrieved", userInfo.getUsername(),
                    userInfo.getRole(), userInfo.getEmail(), userInfo.getAddress(),
                    userInfo.getPhoneNumber());
        }
        return new Response(false, "Failed to retrieve UserInfo by Username.");
    }

    @PostMapping("/updateUserInfo")
    public Response updateUserInfo(@RequestBody UserInfoDto userInfoDto) {
        log.info("Updating user info");
        if (userInfoDto.getUsername() == null || userInfoDto.getUsername().isEmpty()) {
            return new Response(false, "Failed to retrieve username in updateUserInfo request.");
        }

        // Retrieve user info from the database
        UserInfo userInfo = userInfoService.findUserInfoByUsername(userInfoDto.getUsername());
        if (userInfo == null) {
            return new Response(false, "User not found for the provided username.");
        }

        // Update fields if provided in the request
        if (userInfoDto.getEmail() != null && !userInfoDto.getEmail().isEmpty()) {
            userInfo.setEmail(userInfoDto.getEmail());
        }
        if (userInfoDto.getAddress() != null && !userInfoDto.getAddress().isEmpty()) {
            userInfo.setAddress(userInfoDto.getAddress());
        }
        if (userInfoDto.getPhoneNumber() != null && !userInfoDto.getPhoneNumber().isEmpty()) {
            userInfo.setPhoneNumber(userInfoDto.getPhoneNumber());
        }

        // Save updated info back to the database
        try {
            userInfoService.saveUserInfo(userInfo);
            return new Response(true, "User information updated successfully.");
        } catch (Exception e) {
            log.error("Error updating user info", e);
            return new Response(false, "An error occurred while updating user information.");
        }
    }
}

