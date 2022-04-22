package com.example.test.board.service;

import com.example.test.category.entity.Category;
import com.example.test.category.repository.categoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class categoryServiceImpl implements categoryService{

    @Autowired
    categoryRepository categoryrepository;

    @Override
    public Page<Category> categoryPageList(String searchText, Pageable pageable) throws Exception {

        Page<Category> list = categoryrepository.findByBoardNameContainingOrderByIdxDesc(searchText, pageable);

        return list;
    }

    @Override
    public void boardListSave(Category cate) throws Exception {
        categoryrepository.save(cate);
    }
}
