package com.fdu.capstone_instrument_shopping_center.services;

import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserInfoService {

    List<UserInfo> getAllUserInfo();

    void addRandomUserInfo();

    public boolean userExistByUsername(String username);

    Optional<UserInfo> findUserInfoByUsername(String username);

}
