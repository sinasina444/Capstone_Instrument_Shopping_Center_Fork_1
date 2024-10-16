package com.fdu.capstone_instrument_shopping_center.security;

import com.fdu.capstone_instrument_shopping_center.configuration.SecurityConfig;
import com.fdu.capstone_instrument_shopping_center.entity.UserInfo;
import com.fdu.capstone_instrument_shopping_center.security.util.JwtUtil;
import com.fdu.capstone_instrument_shopping_center.services.UserInfoService;
import io.micrometer.core.instrument.config.validate.ValidationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.security.auth.login.CredentialException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final UserInfoService userInfoService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthenticationFilter(UserInfoService userInfoService, JwtUtil jwtUtil) {
        // use a placeholder for authentication manager
        super(authenticationManager -> authenticationManager);
        this.userInfoService = userInfoService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws java.io.IOException, ServletException {
        String path = request.getRequestURI();
        if (path.equals("/User/login") || path.equals("/User/register")) {
            chain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = null;

            if (jwtUtil.validateToken(token)) {
                // Set addtional user authentication in SecurityContext
                username = jwtUtil.extractUsername(token);
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserInfo userInfo = userInfoService.findUserInfoByUsername(username);
                    if(userInfo.getUsername().equals(username)) {
                        boolean isUserExist = userInfoService.userExistByUsername(username);
                        if(isUserExist) {
                            log.info("User {} exist in DB.", username);
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                                    = new UsernamePasswordAuthenticationToken(userInfo.getUsername(),
                                    null, null);

                            // set authentication result into Security context holder
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        } else {
                            log.warn("User {} does not exist.", username);
                            throw new RuntimeException("Username does not exist.");
                        }
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
