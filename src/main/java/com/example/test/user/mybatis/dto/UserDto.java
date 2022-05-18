package com.example.test.user.mybatis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data // get, set
public class UserDto {

    private String username;
    private String pw;
    private String name;
    private String email;
    private Date joinDate;
    private String role;

}
