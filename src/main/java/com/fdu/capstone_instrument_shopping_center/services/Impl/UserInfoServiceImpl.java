package com.fdu.capstone_instrument_shopping_center.services.Impl;

import com.fdu.capstone_instrument_shopping_center.dto.UserDetailDto;
import com.fdu.capstone_instrument_shopping_center.dto.UserInfoDto;
import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
import com.fdu.capstone_instrument_shopping_center.repositories.UserInfoRepository;
import com.fdu.capstone_instrument_shopping_center.services.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    static final int RANDOM_LEN = 6;
    static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public List<UserInfo> getAllUserInfo() {
        return userInfoRepository.findAll();
    }

    @Override
    public void addRandomUserInfo() {
        String randomString = UUID.randomUUID().toString().substring(0,6);
        String emailString = randomString + "@gmail.com";
        Boolean enabled = true;
        String role = "customer";
        UserInfo userInfo = new UserInfo(randomString, randomString, emailString, enabled, role);
        userInfoRepository.save(userInfo);
    }

    @Override
    public boolean userExistByUsername(String username) {
        return userInfoRepository.findUserInfoByUsername(username).isPresent();
    }

    @Override
    public UserInfo findUserInfoByUsername(String username) throws UsernameNotFoundException{
        return userInfoRepository.findUserInfoByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("User does not exist with username: {}." + username));
    }

    @Override
    public UserInfo register(UserInfoDto userDetailDto) {
        if(userInfoRepository.findUserInfoByUsername(userDetailDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }
        UserInfo userInfo = new UserInfo(userDetailDto.getUsername(),
                userDetailDto.getPassword(),
                userDetailDto.getRole());
        userInfo.setAddress(userDetailDto.getAddress());
        userInfo.setRole(userDetailDto.getRole());
        userInfo.setEmail(userDetailDto.getEmail());
        userInfo.setPhoneNumber(userDetailDto.getPhoneNumber());
        userInfo.setEnabled(true);
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoRepository.findUserInfoByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Map UserInfo to userdetails.User
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getAuthorities());
    }
}
