package com.fdu.capstone_instrument_shopping_center.services;

import com.fdu.capstone_instrument_shopping_center.dto.UserInfoDto;
import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserInfoService extends UserDetailsService {

    List<UserInfo> getAllUserInfo();

    void addRandomUserInfo();

    public boolean userExistByUsername(String username);

    UserInfo findUserInfoByUsername(String username);

    public UserInfo register(UserInfoDto userInfoDto);

    void saveUserInfo(UserInfo userInfo);
}
