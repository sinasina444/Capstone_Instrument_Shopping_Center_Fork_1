package com.fdu.capstone_instrument_shopping_center.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserInfoDto implements UserDetailDto{

    private String username;

    private String password;

    private String role;

    private Boolean enabled;

    private String email;

    private String address;

    String phoneNumber;
}
