package com.example.test.login.service;

import com.example.test.login.security.securityUser;
import com.example.test.user.entity.Users;
import com.example.test.user.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class securityUserDetailService implements UserDetailsService {
//public class securityUserDetailService {
    @Autowired
    private userRepository userrepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> optional = userrepository.findById(username);
        if (optional.isPresent()) {
            Users user = optional.get();
            return new securityUser(user);

        } else {
            throw new UsernameNotFoundException(username + "사용자 없음");
        }
    }

}
