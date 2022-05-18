package com.example.test.category.mybatis.service;

import com.example.test.category.mybatis.dto.CategoryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface boardCategoryService {

    List<CategoryDto> categoryList(String searchText) throws Exception;

    long totalBoardCount() throws Exception;

    void boardListSave(CategoryDto cate) throws Exception;

    CategoryDto findById(long idx) throws Exception;

    void boardNameUpdate(String boardUpdateName, String boardName) throws Exception;

    void deleteById(long idx) throws Exception;
}
