package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.LameUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.

    private final LameUserDetailsService lameUserDetailsService;

    @Autowired
    public SuccessUserHandler(LameUserDetailsService lameUserDetailsService) {
        this.lameUserDetailsService = lameUserDetailsService;
    }



    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String username = authentication.getName();
        UserDetails userDetails = lameUserDetailsService.loadUserByUsername(username);
        if (roles.contains("ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else if (roles.contains("USER")) {
            if (userDetails instanceof User) {
                User user = (User) userDetails;
                httpServletResponse.sendRedirect("/user?id=" + user.getId());
            } else {
                httpServletResponse.sendRedirect("/user");
            }
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }


}