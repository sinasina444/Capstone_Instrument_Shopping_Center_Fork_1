package com.fdu.capstone_instrument_shopping_center.security;

import com.fdu.capstone_instrument_shopping_center.configuration.SecurityConfig;
import com.fdu.capstone_instrument_shopping_center.security.util.JwtUtil;
import com.fdu.capstone_instrument_shopping_center.services.UserInfoService;
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
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                // Set addtional user authentication in SecurityContext
                String username = jwtUtil.extractUsername(token);
                boolean isUserExist = userInfoService.userExistByUsername(username);
                if(isUserExist) {
                    log.info("User {} exist.", username);
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    log.info("User {} does not exist.", username);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
