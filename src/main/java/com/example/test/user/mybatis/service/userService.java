package com.example.test.user.mybatis.service;

import com.example.test.user.jpa.entity.Users;
import com.example.test.user.mybatis.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface userService {

    void userSave(UserDto user) throws Exception;

    void userUpdate(UserDto user) throws Exception;

    UserDto userDetail(String username) throws Exception;

    List<UserDto> userList(String searchText, String searchText2) throws Exception;

    List<String> findUsername() throws Exception;

    void deleteById(String username);



}
