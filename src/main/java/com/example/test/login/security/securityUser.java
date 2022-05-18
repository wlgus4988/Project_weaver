package com.example.test.login.security;

import com.example.test.user.jpa.entity.Users;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class securityUser extends User {

    private Users user;

    public securityUser(Users user) {

        //암호화 처리 전까지는 패스워드 앞에 {noop}
        super(user.getUsername(), user.getPw(), AuthorityUtils.createAuthorityList(user.getRole()));
        this.user = user;

    }
}
