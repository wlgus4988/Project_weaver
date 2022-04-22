package com.example.test.board.repository;

import com.example.test.board.entity.BoardFileDto;
import com.example.test.board.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface fileRepository extends JpaRepository<Files, Long> {

    @Query(value ="select FILE_SEQ.NEXTVAL from dual", nativeQuery =true)
    long seq();

    List<Files> findAllByBoardIdx(long boardIdx);

    Files findByIdx(long idx);

}
