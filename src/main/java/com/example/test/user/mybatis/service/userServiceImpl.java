package com.example.test.user.mybatis.service;

import com.example.test.user.jpa.entity.Users;
import com.example.test.user.jpa.repository.userRepository;
import com.example.test.user.mybatis.dto.UserDto;
import com.example.test.user.mybatis.mapper.userMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userServiceImpl implements userService {

    @Autowired
    userMapper usermapper;

    @Override
    public void userSave(UserDto user) throws Exception {
        usermapper.userSave(user);
    }

    @Override
    public void userUpdate(UserDto user) throws Exception {
        usermapper.userUpdate(user);
    }

    @Override
    public UserDto userDetail(String username) throws Exception {
        return usermapper.userDetail(username);
    }

    @Override
    public List<UserDto> userList(String searchText, String searchText2) throws Exception {
        return usermapper.userList(searchText, searchText);
    }

    @Override
    public List<String> findUsername() throws Exception {
        return usermapper.findUsername();
    }

    @Override
    public void deleteById(String username) {
        usermapper.deleteById(username);

    }

}
