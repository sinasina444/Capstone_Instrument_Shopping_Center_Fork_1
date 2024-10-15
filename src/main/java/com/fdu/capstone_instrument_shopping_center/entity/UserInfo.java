package com.fdu.capstone_instrument_shopping_center.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@Table(name="user_info")
public class UserInfo implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="username",nullable=false)
    private String username;

    @Column(name="password",nullable=false)
    private String password;

    @Column(name="email")
    @JsonIgnoreProperties(ignoreUnknown = true)
    String email;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name="enabled")
    Boolean enabled;

    // role: ROLE_ADMIN, ROLE_CUSTOMER, ROLE_SELLER
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name="role", nullable = false)
    String role;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name="address")
    String address;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name="phoneNumber")
    String phoneNumber;

    public UserInfo(){};

    public UserInfo(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserInfo(String username, String password, String email, Boolean enabled, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
