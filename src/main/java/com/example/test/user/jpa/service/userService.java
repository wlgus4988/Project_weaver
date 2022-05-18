package com.example.test.user.jpa.service;

import com.example.test.user.jpa.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface userService {

    void userSave(Users user) throws Exception;

    Users userDetail(String username) throws Exception;

    Page<Users> userPageList(String searchText, Pageable pageable) throws Exception;
}
