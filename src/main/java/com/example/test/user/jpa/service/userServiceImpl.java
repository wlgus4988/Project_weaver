package com.example.test.user.jpa.service;

import com.example.test.user.jpa.entity.Users;
import com.example.test.user.jpa.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
public class userServiceImpl implements userService {

    @Autowired
    userRepository userrepository;

    @Override
    public void userSave(Users user) throws Exception {
        userrepository.save(user);
    }

    @Override
    public Page<Users> userPageList(String searchText, Pageable pageable) throws Exception {

        Page<Users> list = userrepository.findByUsernameContainingOrNameContainingOrderByJoinDateDesc(searchText, searchText, pageable);

        return list;
    }

    @Override
    public Users userDetail(String username) throws Exception {

        Optional<Users> optional = userrepository.findById(username);

        if(optional.isPresent()) { // id가 존재한다면

            Users user = optional.get();

            return user;
        }
        return null;
    }



}
