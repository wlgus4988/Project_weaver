package com.example.test.board.repository;

import com.example.test.board.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface commentRepository extends JpaRepository<Comments,Long> {

    List<Comments> findByBoardIdx(long boardIdx);

}
