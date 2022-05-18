package com.example.test.board.mybatis.mapper;

import com.example.test.board.jpa.entity.Board;
import com.example.test.board.mybatis.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface boardMapper {

    BoardDto findByBoardIdx(long pBoardIdx);
    List<BoardDto> findAll(@Param("searchText") String searchText, @Param("searchText") String searchText2, String boardName);

    void boardSetHitCnt(long boardIdx);

    List<Long> selectGroupNum();
    List<Long> findGroupIdx(long pBoardIdx);
    void boardSave(BoardDto board);
    void boardDelete(@Param("boardIdx") long boardIdx, @Param("NL")String NL);
    void deleteFile(long boardIdx);
    long Seq();
    void boardUpdate(BoardDto board);
   /* List<Long> selectGroupNum(long pBoardIdx);

    List<Long> findGroupIdx(long pBoardIdx);



    void boardSetHitCnt(long boardIdx);

    void deleteFile(long boardIdx);*/
}
