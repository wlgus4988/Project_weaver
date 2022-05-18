package com.example.test.board.jpa.service;

import com.example.test.board.jpa.entity.Comments;
import com.example.test.board.jpa.repository.commentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public class commentServiceImpl implements commentService {

    @Autowired
    commentRepository commentrepository;

    @Override
    public List<Comments> commentsList(long boardIdx) {
        List<Comments> comment = commentrepository.findByBoardIdx(boardIdx);

        System.out.println(comment.toString());
        System.out.println("========================>여기까지");
        return comment;
    }


    @Override
    public void commentSave(Comments comments) {
        commentrepository.save(comments);
    }

    @Override
    public void commentDelete(long idx) {
        commentrepository.deleteById(idx);
    }

}
