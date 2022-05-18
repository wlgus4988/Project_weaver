package com.example.test.category.mybatis.service;

import com.example.test.category.mybatis.dto.CategoryDto;
import com.example.test.category.mybatis.mapper.boardCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class boardCategoryServiceImpl implements boardCategoryService {

    @Autowired
    boardCategoryMapper boardcategorymapper;

    @Override
    public List<CategoryDto> categoryList(String searchText) throws Exception {
        return boardcategorymapper.categoryList(searchText);
    }

    @Override
    public long totalBoardCount() throws Exception {
        return boardcategorymapper.totalBoardCount();
    }

    @Override
    public void boardListSave(CategoryDto cate) throws Exception {
        boardcategorymapper.boardListSave(cate);

    }

    @Override
    public CategoryDto findById(long idx) throws Exception{
        return boardcategorymapper.findById(idx);
    }

    @Override
    public void boardNameUpdate(String boardUpdateName, String boardName) throws Exception{
        boardcategorymapper.boardNameUpdate(boardUpdateName,boardName);

    }

    @Override
    public void deleteById(long idx) throws Exception{
        boardcategorymapper.deleteById(idx);

    }
}
