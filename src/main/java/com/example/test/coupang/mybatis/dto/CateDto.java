package com.example.test.coupang.mybatis.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data // get, set
public class CateDto {


    private long idx;
    // 부모 카테고리
    private long pIdx;
    // 최상위 부모의 그룹 번호
    private long groupNum;
    // 부모 글 밑 순서
    private long groupIdx;
    // 답글 깊이
    private long depth;

    private String deleteYN = "N";

    private String cateName;

}
