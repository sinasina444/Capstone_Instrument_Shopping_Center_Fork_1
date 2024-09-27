package com.fdu.capstone_instrument_shopping_center.services.Impl;

import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
import com.fdu.capstone_instrument_shopping_center.repositories.UserInfoRepository;
import com.fdu.capstone_instrument_shopping_center.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    static final int RANDOM_LEN = 6;
    static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public List<UserInfo> getAllUserInfo() {
        return userInfoRepository.findAll();
    }

    @Override
    public void addRandomUserInfo() {
        String randomString = UUID.randomUUID().toString().substring(0,6);
        String emailString = randomString + "@gmail.com";
        UserInfo userInfo = new UserInfo(randomString, randomString, emailString);
        userInfoRepository.save(userInfo);
    }

}
