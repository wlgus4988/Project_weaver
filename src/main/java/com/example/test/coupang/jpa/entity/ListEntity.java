package com.example.test.coupang.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity // 데이블을 만드는 엔티티 객체 생성
@Table(name = "COUPANG_PRODUCT")
@NoArgsConstructor // 디폴트 생성자
@AllArgsConstructor // 전체 컬럼 생성자
@Data // get, set
@Builder // 객체 생성 도와주는 도구
public class ListEntity {

    @Id
    @Column(name = "PRODUCT_IDX")
    // Auto 시퀀스 방식
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long productIdx;

    // 어떤 카테고리인지
    private long productCategoryNum;



    // 상품명
    private String productName;

    // 상품 내용
    @Column(length = 40000)
    private String productContents;

    // 상품 타입
    private String productType;

    // 금액
    private long productPrice;

    // 배송 종류(로켓, 제트, 일반, 무료)
    private String delivery;


    private String username;


    @Temporal(TemporalType.DATE)
    @Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
    private Date createDatetime;


    // 할인
    @Column(columnDefinition = "number default 0")
    private long discount;

    // 사이즈
    private String optionSize;
    // 색상
    private String optionColor;



    private String ImageSrc;

    @Builder.Default
    @Column(columnDefinition = "CHAR default 'N'")
    private String deleteYN = "N" ;
}
