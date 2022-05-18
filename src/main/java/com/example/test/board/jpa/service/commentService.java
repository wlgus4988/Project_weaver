package com.example.test.board.jpa.service;

import com.example.test.board.jpa.entity.Comments;

import java.util.List;

public interface commentService {

    //List<Comments> commentsList(long boardIdx);
    List<Comments> commentsList(long boardIdx);

    // 등록
    void commentSave(Comments comments);

    // 삭제
    void commentDelete(long idx);

}
