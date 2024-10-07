package com.fdu.capstone_instrument_shopping_center.controllers;

import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
import com.fdu.capstone_instrument_shopping_center.repositories.UserInfoRepository;
import com.fdu.capstone_instrument_shopping_center.response.Response;
import com.fdu.capstone_instrument_shopping_center.services.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/UserInfo")
@Slf4j
public class UserInfoController {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/getUserInfoList")
    public List<UserInfo> getUserInfoList() {
        return userInfoService.getAllUserInfo();
    }

    @PostMapping("/addRandomUserInfo")
    public Response addRandomUserInfo() {
        userInfoService.addRandomUserInfo();
        return new Response(true);
    }

    @GetMapping("/checkUserExist")
    public boolean checkUserExist(@RequestParam(value = "username") String username) {
        log.info("username: {}", username);
        return userInfoService.userExistByUsername(username);
    }

}
