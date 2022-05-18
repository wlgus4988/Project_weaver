package com.example.test.coupang.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // 데이블을 만드는 엔티티 객체 생성
@Table(name = "COUPANG_PRODUCT_OPTION")
@NoArgsConstructor // 디폴트 생성자
@AllArgsConstructor // 전체 컬럼 생성자
@Data // get, set
@Builder // 객체 생성 도와주는 도구
public class OptionEntity {

    @Id
    @Column(name = "IDX")
    // Auto 시퀀스 방식
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idx;

    private long productIdx;

    // 재고
    private long productStock;
    // 사이즈
    private String optionSize;
    // 색상
    private String optionColor;

   /* @Column(columnDefinition = "number default 0")
    private long sellCount;*/


}
