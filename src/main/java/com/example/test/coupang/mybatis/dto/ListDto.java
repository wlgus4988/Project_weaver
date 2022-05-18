package com.example.test.coupang.mybatis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.Date;

@Data // get, set
public class ListDto {

    private long productIdx;

    // 어떤 카테고리인지
    private long productCategoryNum;

    // 상품명
    private String productName;

    private String productContents;

    // 상품 타입
    private String productType = "-";

    // 금액
    private long productPrice;

    // 배송 종류(로켓, 제트, 일반, 무료)
    private String delivery;


    private String username;

    private Date createDatetime;


    // 할인
    private long discount = 0;
    // 사이즈
    private String optionSize = "-";
    // 색상
    private String optionColor = "-";



    private String ImageSrc;

    private String deleteYN = "N" ;
}
