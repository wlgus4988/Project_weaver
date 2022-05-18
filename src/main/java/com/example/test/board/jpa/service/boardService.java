package com.example.test.board.jpa.service;

import com.example.test.board.jpa.entity.Board;
import com.example.test.board.jpa.entity.Files;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface boardService {

    // 글 추가
    void boardSave(Board board, long hitCnt, MultipartHttpServletRequest multipartHttpServletRequest, Files files) throws Exception;

    // 삭제
    void boardDelete(long boardIdx, String NL) throws Exception;

    // 상세 조회
    Board boardDetail(long boardIdx) throws Exception;


}
