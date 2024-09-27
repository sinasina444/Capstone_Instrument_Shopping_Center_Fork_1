package com.fdu.capstone_instrument_shopping_center.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="username",nullable=false)
    private String username;

    @Column(name="password",nullable=false)
    private String password;

    @Column(name="email",nullable=false)
    @JsonIgnoreProperties(ignoreUnknown = true)
    String email;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name="address")
    String address;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name="phoneNumber")
    String phoneNumber;

    public UserInfo(){};

    public UserInfo(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
