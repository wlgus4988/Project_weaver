package com.example.test.user.mybatis.mapper;

import com.example.test.user.mybatis.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface userMapper {

    void userSave(UserDto user) throws Exception;
    void userUpdate(UserDto user) throws Exception;

    UserDto userDetail(String username) throws Exception;

    List<UserDto> userList(@Param("searchText") String searchText, @Param("searchText") String searchText2) throws Exception;

    List<String> findUsername() throws Exception;

    void deleteById(String username);

}
