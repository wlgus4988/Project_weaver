package com.example.test.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class securityConfiguration extends WebSecurityConfigurerAdapter {

/*    @Override
    protected void configure(HttpSecurity security) throws Exception {

        security.authorizeRequests().antMatchers("/","/signUp","login/**","/file-download/**").permitAll();
        security.authorizeRequests().antMatchers("/board/**").hasRole("MEMBER");

        security.csrf().disable();

        security.formLogin().loginPage("/").defaultSuccessUrl("/login/loginSuccess", true);
        security.exceptionHandling().accessDeniedPage("/login/loginFail");

        //security.logout().invalidateHttpSession(true).logoutSuccessUrl("/");
        security.logout().logoutUrl("/login/logout").invalidateHttpSession(true).logoutSuccessUrl("/");

    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .headers().frameOptions().disable();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
