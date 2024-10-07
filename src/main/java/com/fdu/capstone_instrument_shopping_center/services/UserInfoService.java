package com.fdu.capstone_instrument_shopping_center.services;

import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    List<UserInfo> getAllUserInfo();

    void addRandomUserInfo();

    public boolean userExistByUsername(String username);

}
