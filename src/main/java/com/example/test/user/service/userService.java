package com.example.test.user.service;

import com.example.test.user.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface userService {

    void userSave(Users user) throws Exception;

    Users userDetail(String username) throws Exception;

    Page<Users> userPageList(String searchText, Pageable pageable) throws Exception;
}
