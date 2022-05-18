package com.example.test.board.mybatis.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BoardDto {

    private long boardIdx;
    // 부모 글 번호
    private long pBoardIdx;
    // 최상위 부모의 그룹 번호
    private long groupNum;
    // 부모 글 밑 순서
    private long groupIdx;
    // 답글 깊이
    private long depth;
    private String deleteYN;
    private String title;

    private String contents;
    private long hitCnt;
    private String username;

    private long filesCount;

    private Date createDatetime;

    // 어떤 게시판 글인지
    private String Category;
}
