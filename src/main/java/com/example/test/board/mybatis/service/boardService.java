package com.example.test.board.mybatis.service;

import com.example.test.board.jpa.entity.Board;
import com.example.test.board.jpa.entity.Files;
import com.example.test.board.mybatis.dto.BoardDto;
import com.example.test.board.mybatis.dto.FileDto;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface boardService {

    BoardDto findByBoardIdx(long pBoardIdx) throws Exception;

    List<BoardDto> findAll(String searchText, String searchText2, String boardName);

    // 글 추가
    void boardSave(BoardDto board, long hitCnt, MultipartHttpServletRequest multipartHttpServletRequest, FileDto files) throws Exception;

   /* // 삭제
    void boardDelete(long boardIdx, String NL) throws Exception;
*/
    // 상세 조회
    BoardDto boardDetail(long boardIdx) throws Exception;

    List<Long> selectGroupNum() throws Exception;

    List<Long> findGroupIdx(long pBoardIdx) throws Exception;

    void boardDelete(long boardIdx, String NL) throws Exception;

    void deleteFile(long boardIdx);
    void boardUpdate(BoardDto board, long hitCnt, MultipartHttpServletRequest multipartHttpServletRequest, FileDto files) throws Exception;


}
