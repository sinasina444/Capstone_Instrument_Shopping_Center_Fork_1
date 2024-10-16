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
}

