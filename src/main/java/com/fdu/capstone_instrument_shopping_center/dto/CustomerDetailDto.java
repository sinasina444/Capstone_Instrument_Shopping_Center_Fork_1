package com.fdu.capstone_instrument_shopping_center.dto;

import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class CustomerDetailDto implements UserDetailDto{
    private final UserInfo userInfo;


    public CustomerDetailDto(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getRole() {
        return userInfo.getRole();
    }

    @Override
    public Boolean getEnabled() {
        return userInfo.getEnabled();
    }
}
