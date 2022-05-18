package com.example.test.category.jpa.service;

import com.example.test.category.jpa.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface categoryService {

    Page<Category> categoryPageList(String searchText, Pageable pageable) throws Exception;

    void boardListSave(Category cate) throws Exception;

}
