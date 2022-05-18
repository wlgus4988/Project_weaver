package com.example.test.category.mybatis.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data // get, set
public class CategoryDto {

    private long idx;

    // 카테고리 이름
    private String boardName;

    // 해당 카테고리 총 글 개수
    private long boardCount;


}
