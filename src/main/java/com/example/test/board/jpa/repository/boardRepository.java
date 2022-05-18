package com.example.test.board.jpa.repository;

import com.example.test.board.jpa.entity.Board;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface boardRepository extends JpaRepository<Board, Long> {

    Board findByBoardIdx(long pBoardIdx);

    @Query(value = "select GROUP_NUM from BOARD where P_BOARD_IDX = :pBoardIdx", nativeQuery = true)
    List<Long> selectGroupNum(long pBoardIdx);

    @Query(value = "select GROUP_IDX from BOARD where P_BOARD_IDX = :pBoardIdx", nativeQuery = true)
    List<Long> findGroupIdx(long pBoardIdx);

    @Transactional
    @Modifying
    @Query(value="UPDATE BOARD SET DELETEYN = 'Y',TITLE = :NL||'삭제된 글입니다' WHERE BOARD_IDX = :boardIdx", nativeQuery = true)
    void boardDelete(long boardIdx, String NL);

    // 검색 - 사용자 이름 제목 제목으로
    @Query(value= "SELECT * FROM board where (USERNAME LIKE %:searchText% OR TITLE LIKE %:searchText%) AND CATEGORY = :boardName START WITH p_board_idx = 0 CONNECT BY PRIOR BOARD_IDX = P_BOARD_IDX order siblings by GROUP_NUM desc", nativeQuery = true)
    List<Board> findAll(@Param("searchText") String searchText, @Param("searchText") String searchText2, String boardName);

    @Transactional
    @Modifying
    @Query(value="UPDATE BOARD SET files_count = files_count-1 WHERE BOARD_IDX = :boardIdx", nativeQuery = true)
    void deleteFile(long boardIdx);
}


