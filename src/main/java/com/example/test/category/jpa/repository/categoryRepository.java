package com.example.test.category.jpa.repository;

import com.example.test.category.jpa.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface categoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findByBoardNameContainingOrderByIdxDesc(String searchText, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value="UPDATE BOARD SET CATEGORY = :boardUpdateName WHERE CATEGORY = :boardName", nativeQuery = true)
    void boardNameUpdate(String boardUpdateName, String boardName);

    @Transactional
    @Modifying
    @Query(value="UPDATE CATEGORY SET BOARD_COUNT = BOARD_COUNT+ 1 WHERE BOARD_NAME =:boardName", nativeQuery = true)
    void boardCountUpdate(String boardName);

    @Query(value="SELECT COUNT(CATEGORY) FROM BOARD WHERE CATEGORY ='전체 게시판'", nativeQuery = true)
    long totalBoardCount(String boardName);
}
