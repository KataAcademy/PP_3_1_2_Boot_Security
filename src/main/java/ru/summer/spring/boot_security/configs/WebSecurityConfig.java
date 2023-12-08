package ru.summer.spring.boot_security.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import ru.summer.spring.boot_security.service.UserServiceImp;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;

    private final UserServiceImp personServiceImp;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler,
                             UserServiceImp personServiceImp) {
        this.successUserHandler = successUserHandler;
        this.personServiceImp = personServiceImp;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**", "/user/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    // NoOpPasswordEncoder не безопасен,
    // он используется только для учебного проекта
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(personServiceImp)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    // по дефолту у spring security залочен доступ к css файлам
    // пользователям которые не прошли аутентификацию
    // поэтому я их открыл для всех
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
    }

}