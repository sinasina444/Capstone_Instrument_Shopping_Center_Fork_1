package com.fdu.capstone_instrument_shopping_center.dto;

import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;

public interface UserDetailDto {
    public String getUsername();
    public String getPassword();
    public String getRole();
    public Boolean getEnabled();

}
