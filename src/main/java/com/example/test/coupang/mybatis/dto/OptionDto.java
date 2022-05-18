package com.example.test.coupang.mybatis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data // get, set
public class OptionDto {

    private long idx;

    private long productIdx;

    // 재고
    private long productStock;
    // 사이즈
    private String optionSize;
    // 색상
    private String optionColor;


}
