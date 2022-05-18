package com.example.test.category.jpa.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // 데이블을 만드는 엔티티 객체 생성
@Table(name = "CATEGORY")
@NoArgsConstructor // 디폴트 생성자
@AllArgsConstructor // 전체 컬럼 생성자
@Data // get, set
@Builder // 객체 생성 도와주는 도구
public class Category {

    @Id
    // Auto 시퀀스 방식
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idx;

    // 카테고리 이름
    private String boardName;

    // 해당 카테고리 총 글 개수
    private long boardCount;


}
