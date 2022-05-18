package com.example.test.coupang.jpa.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // 데이블을 만드는 엔티티 객체 생성
@Table(name = "COUPANG_CATEGORY")
@NoArgsConstructor // 디폴트 생성자
@AllArgsConstructor // 전체 컬럼 생성자
@Data // get, set
@Builder // 객체 생성 도와주는 도구
public class CateEntity {

    @Id
    @Column(name = "IDX")
    // Auto 시퀀스 방식
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idx;

    // 부모 카테고리
    private long pIdx;
    // 최상위 부모의 그룹 번호
    private long groupNum;
    // 부모 글 밑 순서
    private long groupIdx;
    // 답글 깊이
    private long depth;

    @Column(columnDefinition = "CHAR default 'N'")
    private String deleteYN = "N";

    private String cateName;

/*    @Temporal(TemporalType.DATE)
    @Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
    private Date createDatetime;*/

}
