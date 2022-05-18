package com.example.test.board.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity // 데이블을 만드는 엔티티 객체 생성
@Table(name = "BOARD")
@NoArgsConstructor // 디폴트 생성자
@AllArgsConstructor // 전체 컬럼 생성자
@Data // get, set
@Builder // 객체 생성 도와주는 도구

public class Board {

    @Id
    @Column(name = "BOARD_IDX")
    // Auto 시퀀스 방식
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long boardIdx;

    // 부모 글 번호
    private long pBoardIdx;
    // 최상위 부모의 그룹 번호
    private long groupNum;
    // 부모 글 밑 순서
    private long groupIdx;
    // 답글 깊이
    private long depth;

    @Column(columnDefinition = "CHAR default 'N'")
    private String deleteYN = "N";
    private String title;

    @Column(length = 50000)
    private String contents;
    private long hitCnt;
    private String username;

    @Column(columnDefinition = "number default 0")
    private long filesCount = 0;

    @Temporal(TemporalType.DATE)
    @Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
    private Date createDatetime;


    // 어떤 게시판 글인지
    private String Category;

    /*private ArrayList arr;*/

}
